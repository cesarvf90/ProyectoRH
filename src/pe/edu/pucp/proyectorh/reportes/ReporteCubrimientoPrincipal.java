package pe.edu.pucp.proyectorh.reportes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.ColaboradorEquipoTrabajo;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCPrincipal.PeriodoDTO;
import pe.edu.pucp.proyectorh.reportes.ReportePersonalBSCPrincipal.getReporteColaborador;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class ReporteCubrimientoPrincipal extends Fragment {
	
	private Spinner spinnerPuesto;
	private Spinner spinnerArea;
	private Button btnSubmit;
	List<String> listaArea ;
	List<String> listaPuestos;
	
	int areaSelec;
	int puestoSelec;
	String titulo;
	
	List<AreaRDTO> areas;


	
	public ReporteCubrimientoPrincipal(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportecubrimiento,
				container, false);
		
		//fuente
		TextView txt = (TextView) rootView.findViewById(R.id.reportecub1titulo);  
		
		
		spinnerArea = (Spinner) rootView.findViewById(R.id.reportecubspinnerArea);
		
		spinnerPuesto = (Spinner) rootView.findViewById(R.id.reportecubspinnerPuesto);
		listaArea = new ArrayList<String>();
		listaPuestos = new ArrayList<String>();

		obtenerlistaPuestos();
		

		
		

		btnSubmit = (Button) rootView.findViewById(R.id.reportecubbtnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {

					  
					  
					  if( PersistentHandler.buscarArchivo(getActivity(), "ReporteOferta" + puestoSelec +".txt")){
						  
						  ReporteCubrimientoGrafico fragment = new ReporteCubrimientoGrafico();
						  Bundle argumentos = new Bundle();
					      argumentos.putInt("PuestoSelec", puestoSelec);
					      argumentos.putString("titulo", titulo);
					      fragment.setArguments(argumentos);
					      
						  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
						  ft.replace(R.id.opcion_detail_container, fragment);
						  ft.addToBackStack(null);
						  ft.commit();
							
						}
					  else{
						  obtenerReporteOffline(puestoSelec);
					  }

					  
					  /*
					  ReporteCubrimientoGrafico fragment = new ReporteCubrimientoGrafico();
					  Bundle argumentos = new Bundle();
				      argumentos.putInt("PuestoSelec", puestoSelec);
				      argumentos.putString("titulo", titulo);
				      
				      String fechadesde = "" + dia;
				      if (mes<10) fechadesde = fechadesde + "/" + "0" + mes + "/" + anho;
				      else fechadesde  = fechadesde + "/" + mes + "/" + anho;
				      
				      String fechahasta = "" + diafin;
				      if (mesfin<10) fechahasta = fechahasta + "/" + "0" + mesfin + "/" + anhofin;
				      else fechahasta  = fechahasta + "/" + mesfin + "/" +  anhofin;
				      
				      argumentos.putString("fechaini", fechadesde);
				      argumentos.putString("fechafin", fechahasta);
				      fragment.setArguments(argumentos);
				      
					  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
					  ft.replace(R.id.opcion_detail_container, fragment);
					  ft.addToBackStack(null);
					  ft.commit();
				  */
				  
				  
			  }
		});
		
		customizarEstilos(getActivity(), rootView);
		return rootView;
	}
	
	protected void obtenerReporteOffline(int idpuesto){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			
			String request = ReporteServices.obtenerOfertas + "?puesto=" + idpuesto;

			new getReporteOferta(getActivity()).execute(request);
			
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
	
	public class getReporteOferta extends AsyncCall{
		
		public getReporteOferta(Activity activity) {
			super(activity);
		}
	
	
		@Override
		protected void onPostExecute(String result) {
			
			ocultarMensajeProgreso();

			System.out.println("Recibido: " + result.toString());
			
			
			String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

			PersistentHandler.agregarArchivoPersistente(currentDateTimeString + "\n" + result, getActivity(), "ReporteOferta" + puestoSelec +".txt");
		      
			ReporteCubrimientoGrafico fragment = new ReporteCubrimientoGrafico();
			  Bundle argumentos = new Bundle();
		      argumentos.putInt("PuestoSelec", puestoSelec);
		      argumentos.putString("titulo", titulo);
		      fragment.setArguments(argumentos);
		      
			  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
			  ft.replace(R.id.opcion_detail_container, fragment);
			  ft.addToBackStack(null);
			  ft.commit();
			
			
		}
		
	}
	
	
	protected void obtenerlistaPuestos(){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = ReporteServices.obtenerAreas;

			new getPuestos().execute(request);
			
		} else {
			// Se muestra mensaje de error de conexion con el servicio
			/*
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Error de conexción");
			builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();*/
			
			//obtener los de offline
			areas = PersistentHandler.getAreasFromFile(getActivity(), "areasReporte.txt");
			for(int i = 0; i<areas.size();i++){
				
				listaArea.add(areas.get(i).getNombreArea());
				
			}
			
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaArea);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerArea.setAdapter(dataAdapter);
			spinnerArea.setOnItemSelectedListener(new OnItemSelectedListener(){
				
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					
					areaSelec=pos;
				    ArrayList<String> puestos = new ArrayList<String>();
				    for (int i=0;i<areas.get(areaSelec).getPuestos().size();i++){
				    	puestos.add(areas.get(areaSelec).getPuestos().get(i).getNombrePuesto());
				    }
				    cargarPuestos(puestos);
				  }
				
			
				@Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
				
			 });
			
			
			
		}

	}
	
	public class getPuestos extends AsyncCall {
		/*
		@Override
		protected void onPreExecute(){
			pbarra.setVisibility(View.VISIBLE);
		}
		*/
		@Override
		protected void onPostExecute(String result) {

			System.out.println("Recibido: " + result.toString());
			try{
			Gson gson = new GsonBuilder().create();
			areas = gson.fromJson(result,
					new TypeToken<List<AreaRDTO>>(){}.getType());
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
			
			if (areas.size()>0){
				
				String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
				PersistentHandler.agregarArchivoPersistente(currentDateTimeString + "\n" + result, getActivity(), "areasReporte.txt");
				
			}

			for(int i = 0; i<areas.size();i++){
				
				listaArea.add(areas.get(i).getNombreArea());
				
			}
			
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaArea);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerArea.setAdapter(dataAdapter);
			spinnerArea.setOnItemSelectedListener(new OnItemSelectedListener(){
				
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					
					areaSelec=pos;
				    ArrayList<String> puestos = new ArrayList<String>();
				    for (int i=0;i<areas.get(areaSelec).getPuestos().size();i++){
				    	puestos.add(areas.get(areaSelec).getPuestos().get(i).getNombrePuesto());
				    }
				    cargarPuestos(puestos);
				  }
				
			
				@Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
				
			 });
			
			
			
			
		}
	}
	
	public void cargarPuestos(ArrayList<String> lista){
		ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPuesto.setAdapter(dataAdapter);
		spinnerPuesto.setOnItemSelectedListener(new OnItemSelectedListener(){
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				
			/*	Toast.makeText(parent.getContext(), 
					"seleccionado : " + parent.getItemAtPosition(pos).toString() + " id: " + listaPeriodos.get(pos).getID(),
					Toast.LENGTH_SHORT).show(); */
				
				puestoSelec = areas.get(areaSelec).getPuestos().get(pos).getIdPuesto(); 
				titulo = parent.getItemAtPosition(pos).toString();

			  }
			
		
			@Override
			  public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			  }
			
		 });
	}
	
	public class PuestoDTO {

		private int idPuesto;
		private String nombrePuesto;
		public int getIdPuesto() {
			return idPuesto;
		}
		public void setIdPuesto(int idPuesto) {
			this.idPuesto = idPuesto;
		}
		public String getNombrePuesto() {
			return nombrePuesto;
		}
		public void setNombrePuesto(String nombrePuesto) {
			this.nombrePuesto = nombrePuesto;
		}
	     
	      
	     
	}
	
	public class AreaRDTO
    {
        private int idArea;
        private String nombreArea;
        private List<PuestoDTO> Puestos;
		public int getIdArea() {
			return idArea;
		}
		public void setIdArea(int idArea) {
			this.idArea = idArea;
		}
		public String getNombreArea() {
			return nombreArea;
		}
		public void setNombreArea(String nombreArea) {
			this.nombreArea = nombreArea;
		}
		public List<PuestoDTO> getPuestos() {
			return Puestos;
		}
		public void setPuestos(List<PuestoDTO> puestos) {
			Puestos = puestos;
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
