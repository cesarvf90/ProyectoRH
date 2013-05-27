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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class ReporteObjetivosBSCPrincipal extends Fragment {
	
	private Spinner spinnerPeriodo;
	private Button btnSubmit;
	private ProgressBar pbarra; 
	
	int periodoSelec;
	String titulo;
	
	List<PeriodoDTO> listaPeriodos;
	List<String> lista ;
	
	
	public ReporteObjetivosBSCPrincipal(){
	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportebsc1principal,
				container, false);
		pbarra = (ProgressBar) rootView.findViewById(R.id.reportebscprogressbar);

		
		spinnerPeriodo = (Spinner) rootView.findViewById(R.id.reportebscspinner);
		lista = new ArrayList<String>();
		obtenerlistaPeriodos();

		btnSubmit = (Button) rootView.findViewById(R.id.reportebscbtnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
		 /*
			    Toast.makeText(v.getContext(),
				"Seleccionado "+ String.valueOf(spinnerPeriodo.getSelectedItem()), 
					Toast.LENGTH_SHORT).show();
			*/    
				 // obtenerlistaPeriodos();

			      ReporteObjetivosBSCPerspectivas fragment = new ReporteObjetivosBSCPerspectivas();
			      
			      Bundle argumentos = new Bundle();
			      argumentos.putInt("PeriodoSelec", periodoSelec);
			      argumentos.putString("titulo", titulo);
			      fragment.setArguments(argumentos);
			      
				  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				  ft.replace(R.id.opcion_detail_container, fragment);
				  ft.addToBackStack(null);
				  ft.commit();
				  
			  }
		 
			});

		return rootView;
	}
	
	
	protected void obtenerlistaPeriodos(){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = ReporteServices.obtenerPeriodos;
			//String request = Servicio.LoginService;

			new getPeriodos().execute(request);
			
		} else {
			// Se muestra mensaje de error de conexion con el servicio
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Error de conexción");
			builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
		}

		
		
	}
	
	public class getPeriodos extends AsyncCall {
		/*
		@Override
		protected void onPreExecute(){
			pbarra.setVisibility(View.VISIBLE);
		}
		*/
		@Override
		protected void onPostExecute(String result) {

			System.out.println("Recibido: " + result.toString());

			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			List<PeriodoDTO> periodos = gson.fromJson(result,
					new TypeToken<List<PeriodoDTO>>(){}.getType());
			
			
			listaPeriodos = periodos;
			//lista = new ArrayList<String>();
			for(int i =0; i<listaPeriodos.size();i++){
				
				//System.out.println(listaPeriodos.get(i).getFechaInicio().toString());
				//lista.add("Periodo " + sdf.format(listaPeriodos.get(i).getFechaInicio()) + " - " + sdf.format(listaPeriodos.get(i).getFechaFin()));
				lista.add(listaPeriodos.get(i).getNombre());
				
			}
			
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerPeriodo.setAdapter(dataAdapter);
			spinnerPeriodo.setOnItemSelectedListener(new OnItemSelectedListener(){
				
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					
				/*	Toast.makeText(parent.getContext(), 
						"seleccionado : " + parent.getItemAtPosition(pos).toString() + " id: " + listaPeriodos.get(pos).getID(),
						Toast.LENGTH_SHORT).show(); */
					
					periodoSelec = listaPeriodos.get(pos).getID(); //aqui idobjetivo selec
					titulo = parent.getItemAtPosition(pos).toString();

				  }
				
			
				@Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
				
			});
			
			
		}
	}

	public class PeriodoDTO {
		private int ID ;
		private String Nombre ;

		private Date FechaInicio ;

		private Date FechaFin ;
		private String FechaFinDisplay ;
		private int BSCID ;
		
        
        public PeriodoDTO() {
		}


		public int getID() {
			return ID;
		}


		public void setID(int iD) {
			ID = iD;
		}


		public String getNombre() {
			return Nombre;
		}


		public void setNombre(String nombre) {
			Nombre = nombre;
		}


		public Date getFechaInicio() {
			return FechaInicio;
		}


		public void setFechaInicio(Date fechaInicio) {
			FechaInicio = fechaInicio;
		}


		public Date getFechaFin() {
			return FechaFin;
		}


		public void setFechaFin(Date fechaFin) {
			FechaFin = fechaFin;
		}


		public String getFechaFinDisplay() {
			return FechaFinDisplay;
		}


		public void setFechaFinDisplay(String fechaFinDisplay) {
			FechaFinDisplay = fechaFinDisplay;
		}


		public int getBSCID() {
			return BSCID;
		}


		public void setBSCID(int bSCID) {
			BSCID = bSCID;
		}


	}
	
	
	
	
	
	
	

}
