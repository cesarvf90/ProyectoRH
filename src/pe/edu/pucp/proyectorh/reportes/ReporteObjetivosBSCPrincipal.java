package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import pe.edu.pucp.proyectorh.DetalleFragment;
import pe.edu.pucp.proyectorh.R;
<<<<<<< HEAD
import pe.edu.pucp.proyectorh.LoginActivity.LoginUsuario;
import pe.edu.pucp.proyectorh.LoginActivity.RespuestaLogin;
import pe.edu.pucp.proyectorh.administracion.RendirEvaluacionesFragment;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
=======
import pe.edu.pucp.proyectorh.reclutamiento.EvaluacionPostulanteFragment;
>>>>>>> 240f26c12478f437554d5c9dbf4e136987938720
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.app.AlertDialog;
import android.database.sqlite.*;

public class ReporteObjetivosBSCPrincipal extends Fragment {
	
	private Spinner spinnerPeriodo;
	private Button btnSubmit;
	private ProgressBar pbarra; 
	
	int periodoSelec;
	String titulo;
	
	List<PeriodoDTO> listaPeriodos;
	
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
		
		//System.out.println("entre a obtainlista");
		
		//obtenerlistaPeriodos();
		
		List<String> lista = new ArrayList<String>();
		
		lista.add("2013-1");
		lista.add("2012-3");
		lista.add("2012-2");
		lista.add("2012-1");
		//lista.add(listaPeriodos.get(0).getNombre());
		
		
		
		ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPeriodo.setAdapter(dataAdapter);
		spinnerPeriodo.setOnItemSelectedListener(new OnItemSelectedListener(){
			
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				/*
				Toast.makeText(parent.getContext(), 
					"seleccionado : " + parent.getItemAtPosition(pos).toString(),
					Toast.LENGTH_SHORT).show();
				*/
				periodoSelec = 1; //aqui idobjetivo selec
				titulo = parent.getItemAtPosition(pos).toString();

			  }
			
		
			@Override
			  public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			  }
			
		});
			
		btnSubmit = (Button) rootView.findViewById(R.id.reportebscbtnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
		 /*
			    Toast.makeText(v.getContext(),
				"Seleccionado "+ String.valueOf(spinnerPeriodo.getSelectedItem()), 
					Toast.LENGTH_SHORT).show();
			*/    
				  obtenerlistaPeriodos();

			      ReporteObjetivosBSCPerspectivas fragment = new ReporteObjetivosBSCPerspectivas();
			      
			      Bundle argumentos = new Bundle();
			      argumentos.putString("PeriodoSelec", titulo);
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
			//String request = ReporteServices.obtenerPeriodos;
			String request = Servicio.LoginService;

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
		
		@Override
		protected void onPreExecute(){
			pbarra.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected void onPostExecute(String result) {

			System.out.println("Recibido: " + result.toString());
			/*

			Gson gson = new Gson();
			List<PeriodoDTO> periodos = gson.fromJson(result,
					new TypeToken<List<PeriodoDTO>>(){}.getType());
			System.out.println("retorno WS: " + periodos.get(0).getNombre());
			
			listaPeriodos = periodos;
			*/
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
