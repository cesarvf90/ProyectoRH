package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ReporteCubrimientoPrincipal extends Fragment {
	
	private Spinner spinnerPuesto;
	private Button btnSubmit;
	List<String> lista ;
	
	int puestoSelec;
	String titulo;
	
	List<PuestoDTO> puestos;
	
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
		
		spinnerPuesto = (Spinner) rootView.findViewById(R.id.reportecubspinner);
		lista = new ArrayList<String>();
		//titulo="Puesto X";
		obtenerlistaPuestos();

		btnSubmit = (Button) rootView.findViewById(R.id.reportecubbtnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
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
		});
		
		
		return rootView;
	}
	
	protected void obtenerlistaPuestos(){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = ReporteServices.obtenerPuestos;

			new getPuestos().execute(request);
			
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

			Gson gson = new GsonBuilder().create();
			puestos = gson.fromJson(result,
					new TypeToken<List<PuestoDTO>>(){}.getType());
			

			for(int i = 0; i<puestos.size();i++){
				
				lista.add(puestos.get(i).getNombre());
				
			}
			
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerPuesto.setAdapter(dataAdapter);
			spinnerPuesto.setOnItemSelectedListener(new OnItemSelectedListener(){
				
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					
				/*	Toast.makeText(parent.getContext(), 
						"seleccionado : " + parent.getItemAtPosition(pos).toString() + " id: " + listaPeriodos.get(pos).getID(),
						Toast.LENGTH_SHORT).show(); */
					
					puestoSelec = puestos.get(pos).getID(); //aqui idobjetivo selec
					titulo = parent.getItemAtPosition(pos).toString();

				  }
				
			
				@Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
				
			});
			
			
		}
	}
	
	 public class PuestoDTO
	    {

	        public int ID;
	        public String Nombre;
	        public String Descripcion;       
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
			public String getDescripcion() {
				return Descripcion;
			}
			public void setDescripcion(String descripcion) {
				Descripcion = descripcion;
			}
			public int getAreaID() {
				return AreaID;
			}
			public void setAreaID(int areaID) {
				AreaID = areaID;
			}
			public int getPuestoSuperiorID() {
				return PuestoSuperiorID;
			}
			public void setPuestoSuperiorID(int puestoSuperiorID) {
				PuestoSuperiorID = puestoSuperiorID;
			}
			public int AreaID;
	        public int PuestoSuperiorID;
	}
	
	
	
	
	
	

}
