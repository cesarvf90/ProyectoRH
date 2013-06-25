package pe.edu.pucp.proyectorh.reportes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.reportes.ReporteCubrimientoGrafico.DataObject;
import pe.edu.pucp.proyectorh.reportes.ReporteCubrimientoGrafico.InterfaceChartLineal;
import pe.edu.pucp.proyectorh.reportes.ReporteCubrimientoGrafico.ROfertasLaborales;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCPerspectivas.getReportePeriodoRef;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class ReportePersonalBSCGrafico extends Fragment {
	
	int idcolaborador;
	WebView browser;
	Spinner spinnerPeriodo;
	List<HistoricoBSC> historico;
	Button btnDetalle;
	int posicion=0;
	
	TextView displayFecha;
	ProgressBar pbarra;
	
	Button btnRefrescar;
	
	public ReportePersonalBSCGrafico(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportepersonalbsc2grafico,
				container, false);
		
		idcolaborador = getArguments().getInt("ColaboradorSelec");
		System.out.println("idcolaborador: " + idcolaborador);
		spinnerPeriodo = (Spinner) rootView.findViewById(R.id.reporteperbscspinnerGraf);
		btnDetalle = (Button) rootView.findViewById(R.id.reporteperbscbtnDetalle);
		
		displayFecha = (TextView)rootView.findViewById(R.id.reporteperbscsDisplayfecha);
		pbarra = (ProgressBar) rootView.findViewById(R.id.reporteperbscprogressbarRef);
		
		
		browser = (WebView)rootView.findViewById(R.id.reporteperbscWebkit);
		
		
		String titulo = getArguments().getString("titulo");
		// set value into textview
		TextView textView = (TextView)rootView.findViewById(R.id.reporteperbscTitulografico);
		textView.setText(titulo);
		
		btnRefrescar = (Button)rootView.findViewById(R.id.reporteperbscsActualizar);
		
		btnRefrescar.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
				  obtenerReporteOffline(idcolaborador);
				  
				  
			  }
		});
		
		
		historico = new ArrayList<HistoricoBSC>();
		
		cargarGraficoHistorico(idcolaborador);
		
		customizarEstilos(getActivity(), rootView);
		
		return rootView;
	}
	
	protected void obtenerReporteOffline(int idColab){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			
			String request = ReporteServices.obtenerHistoricoObjetivos + "?idColaborador=" + idColab;

			new getReporteObjPerRef().execute(request);
			
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
	
	public class getReporteObjPerRef extends AsyncCall{
		
		@Override
		protected void onPreExecute(){
			pbarra.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			pbarra.setVisibility(View.INVISIBLE);
			
			String trama = result;
			
			System.out.println("Recibido: " + result.toString());
			
			List<HistoricoBSC> pruebaHistorico=null;
			
			//prueba
			try{
				Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
				pruebaHistorico = gson.fromJson(trama, new TypeToken<List<HistoricoBSC>>(){}.getType()); 
				
			}
			catch(Exception e){
				Toast.makeText(getActivity(), "Error al actualizar el reporte", Toast.LENGTH_SHORT).show();
				
				
			}
			
			if (pruebaHistorico!=null){
			
				String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
		
				PersistentHandler.agregarArchivoPersistente(currentDateTimeString + "\n" + result, getActivity(), "ObjPersonal" + idcolaborador +".txt");
				
				//actualizar gridview
				cargarGraficoHistorico(idcolaborador);
			
			}
		}
		
	}
	
	public void cargarGraficoHistorico(int idcolab){

		System.out.println("entre a cargarhistorico de archivo");
		historico = null;
		
		historico = PersistentHandler.getHistoricoBSCFromFile(getActivity(), "ObjPersonal" + idcolaborador +".txt");
		  if (historico != null){
			  
			  String fechaarch = PersistentHandler.getFechaReporte(getActivity(), "ObjPersonal" + idcolaborador +".txt");
			  displayFecha.setText("Fecha de reporte: " + fechaarch);
			  
			  
			  if (historico.size()==0){
					 String summary = "<html><body>No se encontraron resultados</body></html>";
					 browser.loadData(summary, "text/html", null);
			  }
			  else{
			  
			  ArrayList<String> listaPeriodos = new ArrayList<String> ();
			  
			  
				for (int i=0; i<historico.size();i++ ){
					listaPeriodos.add(historico.get(i).getNombrePeriodo());
				}
				
				ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaPeriodos);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerPeriodo.setAdapter(dataAdapter);
				spinnerPeriodo.setOnItemSelectedListener(new OnItemSelectedListener(){
					
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
						posicion = pos;
					   
						
						int [] objetivos = new int[3];
						
						for(int i=0;i<historico.get(pos).getObjetivos().size();i++){
							
							if (historico.get(pos).getObjetivos().get(i).getAvance() == 100) objetivos[0] = objetivos[0] + 1; 
							else if (historico.get(pos).getObjetivos().get(i).getAvance() > 75) objetivos[1] = objetivos[1] + 1;
							else objetivos[2] = objetivos[2] + 1; 
						}
						
						//habilitamos javascript y flash
						browser.getSettings().setJavaScriptEnabled(true);
						browser.getSettings().setPluginsEnabled(true);

						DataObject data = new DataObject(objetivos);
						
						InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);
						
						browser.addJavascriptInterface(intface, "Android");
						
						
						browser.loadUrl("file:///android_asset/ReporteBSCpersonal.html");

					  }
					
				
					@Override
					  public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					  }
					
				});
				
				btnDetalle.setOnClickListener(new OnClickListener() {
					 
					  @Override
					  public void onClick(View v) {
						  
						  if (historico.size()>0){
						  
						  	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
							builder.setTitle("Detalle del periodo");
							String cadena = "Avan.   Objetivo\n";
							for(int i=0;i<historico.get(posicion).getObjetivos().size();i++){
								cadena = cadena + historico.get(posicion).getObjetivos().get(i).getAvance() + "%";
								if (historico.get(posicion).getObjetivos().get(i).getAvance() < 10) cadena = cadena + "    ";
								else if (historico.get(posicion).getObjetivos().get(i).getAvance() < 100) cadena = cadena + "  ";
								
								cadena = cadena + "   " + historico.get(posicion).getObjetivos().get(i).getDescripcion() + "\n";
							}
							
							builder.setMessage(cadena);
							builder.setCancelable(false);
							builder.setPositiveButton("Ok", null);
							builder.create();
							builder.show();
						  }
					  }
				});
			  
		  }
		  }
		  else{
			  displayFecha.setText("No se puede recuperar la información");
		  }
		
	}
	
	
	
	public class DataObject {
		
		int[] objetivos; 
		
		public DataObject(int[] objetivos){
			this.objetivos = objetivos;
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


	}
	
	
	
	public class HistoricoBSC
	{
	        private int idperiodo;
	        
	        private String nombrePeriodo;

	        private String nombreColaborador;

	        private List<ObjetivoDTO> objetivos;
	        
	        

			public String getNombrePeriodo() {
				return nombrePeriodo;
			}

			public void setNombrePeriodo(String nombrePeriodo) {
				this.nombrePeriodo = nombrePeriodo;
			}

			public int getIdperiodo() {
				return idperiodo;
			}

			public void setIdperiodo(int idperiodo) {
				this.idperiodo = idperiodo;
			}

			public String getNombreColaborador() {
				return nombreColaborador;
			}

			public void setNombreColaborador(String nombreColaborador) {
				this.nombreColaborador = nombreColaborador;
			}

			public List<ObjetivoDTO> getObjetivos() {
				return objetivos;
			}

			public void setObjetivos(List<ObjetivoDTO> objetivos) {
				this.objetivos = objetivos;
			}
	         
	         
	}
	
	private void customizarEstilos(Context context, View view) {
		try {
			if (view instanceof ViewGroup) {
				ViewGroup vg = (ViewGroup) view;
				for (int i = 0; i < vg.getChildCount(); i++) {
					View child = vg.getChildAt(i);
					customizarEstilos(context, child);
				}
			} else if (view instanceof TextView) {
			((TextView) view).setTypeface(Typeface.createFromAsset(
			context.getAssets(), "OpenSans-Light.ttf"));
		}
		} catch (Exception e) {
		}
	}
		
	

}
