package pe.edu.pucp.proyectorh.reportes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.reportes.Reporte360Grafico.DataObject;
import pe.edu.pucp.proyectorh.reportes.Reporte360Grafico.InterfaceChartLineal;
import pe.edu.pucp.proyectorh.reportes.Reporte360Grafico.RNotaxTipoEvaluador;
import pe.edu.pucp.proyectorh.reportes.Reporte360Grafico.RProcesosEvaluacion;
import pe.edu.pucp.proyectorh.reportes.Reporte360Grafico.getProcesos;
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
import android.widget.AdapterView.OnItemSelectedListener;

public class Reporte360Detalle extends Fragment{

	private WebView browser;
	int objSelec;
	String userColaborador;
	private Spinner spinnerCapacidad;
	List<String> lista ;
	List<RProcesosEvaluacion> procesosEval;
	private Button btnSubmit;
	
	
	public Reporte360Detalle(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reporte360detalle,
				container, false);
		
		spinnerCapacidad = (Spinner) rootView.findViewById(R.id.reporte360detallespinner);
		lista = new ArrayList<String>();
				
			
		userColaborador = LoginActivity.getUsuario().getUsername();
		browser = (WebView)rootView.findViewById(R.id.reporte360detalleWebkitGLineal);
		
		cargarGraficoProcesosEvaluacionDetalle(userColaborador);
		//browser.loadUrl("file:///android_asset/Reporte360chart.html");
		
		/*
		String titulo = getArguments().getString("titulo");
		// set value into textview
		TextView textView = (TextView)rootView.findViewById(R.id.reporte360Titulografico);
		textView.setText(titulo);*/
		
		return rootView;
	}
	
	
	
	public void cargarGraficoProcesosEvaluacionDetalle(String userColaborador){
					
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = ReporteServices.obtenerProcesosEvaluacion + "?userName=" + userColaborador;

			new getDetalle().execute(request);
			
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
	
	public class getDetalle extends AsyncCall {

		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			
			try{
						
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			procesosEval = gson.fromJson(result,new TypeToken<List<RProcesosEvaluacion>>(){}.getType());
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
				 String summary = "<html><body>No se encontraron capacidades ya que no hay procesos de evaluación</body></html>";
				 browser.loadData(summary, "text/html", null);
			}
			else{				
				ArrayList<String> evaluadores = new ArrayList<String>();
				ArrayList<Integer> notas = new ArrayList<Integer>();
				String posEval=getArguments().getString("Nomproceso");
				
				for (int i=0;i<procesosEval.size();i++){
					if(procesosEval.get(i).getProcesoNombre().equals(posEval)){
						for (int j=0;j<procesosEval.get(i).getCompetenciasEvaluadas().size();j++){
							if (j==0){
							for (int k=0;k<procesosEval.get(i).getCompetenciasEvaluadas().get(j).getNotasParciales().size();k++){
								evaluadores.add(procesosEval.get(i).getCompetenciasEvaluadas().get(j).getNotasParciales().get(k).getTipoEvaluador());
								notas.add(procesosEval.get(i).getCompetenciasEvaluadas().get(j).getNotasParciales().get(k).geNotaParcial());
							}		
												
							}
							
							lista.add(procesosEval.get(i).getCompetenciasEvaluadas().get(j).getCompetenciaNombre());	
							
					}
											
					}
					}
				
				ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerCapacidad.setAdapter(dataAdapter);
				spinnerCapacidad.setOnItemSelectedListener(new OnItemSelectedListener(){
					
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
						
						ArrayList<String> evaluadores = new ArrayList<String>();
						ArrayList<Integer> notas = new ArrayList<Integer>();
						
 
						for (int i=0;i<procesosEval.size();i++){

							for (int j=0;j<procesosEval.get(i).getCompetenciasEvaluadas().size();j++){
								if (j==pos){
								for (int k=0;k<procesosEval.get(i).getCompetenciasEvaluadas().get(j).getNotasParciales().size();k++){
									evaluadores.add(procesosEval.get(i).getCompetenciasEvaluadas().get(j).getNotasParciales().get(k).getTipoEvaluador());
									notas.add(procesosEval.get(i).getCompetenciasEvaluadas().get(j).getNotasParciales().get(k).geNotaParcial());
								}					
				
								}
						
						}
						
					}

						
						browser.getSettings().setJavaScriptEnabled(true);
						browser.getSettings().setPluginsEnabled(true);
						DataObject data = new DataObject(evaluadores, notas);
									
						InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);				
						
						browser.addJavascriptInterface(intface, "Android");				
						
						browser.loadUrl("file:///android_asset/Reporte360detallechart.html");

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
				
				browser.loadUrl("file:///android_asset/Reporte360detallechart.html");
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

	
	public class RProcesosEvaluacion
	{
	        private String procesoNombre;
	        private List<RNotaxTipoEvaluador> notasParciales;
	        private int notaFinal;
	        private List<RCapacidadxTipoEvaluador>competenciasEvaluadas;
	        
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
			public List<RCapacidadxTipoEvaluador> getCompetenciasEvaluadas() {
				return competenciasEvaluadas;
			}
			public void setCompetenciasEvaluadas(
					List<RCapacidadxTipoEvaluador> competenciasEvaluadas) {
				this.competenciasEvaluadas = competenciasEvaluadas;
			}
						
					
	}
	
	public class RCapacidadxTipoEvaluador{
		public String competenciaNombre;
		private List<RNotaxTipoEvaluador> notasParciales;
        
        public String getCompetenciaNombre() {
			return competenciaNombre;
		}
		public void setCompetenciaNombre(String competenciaNombre) {
			this.competenciaNombre = competenciaNombre;
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
