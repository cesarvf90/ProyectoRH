package pe.edu.pucp.proyectorh.reportes;


import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.reportes.ReporteCubrimientoPrincipal.PuestoDTO;
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
import android.widget.Toast;
import android.widget.ToggleButton;
public class Reporte360 extends Fragment{
	
	private Spinner spinnerArea;
	private Button btnSubmit;
	List<String> lista ;
	
	int areaSelec;
	String titulo;
	private ToggleButton botonRequerido, boton360, botonPonderado;
	int selectRequerido, select360, selectPonderado;
	
	List<PuestoDTO> areas;
	
	public Reporte360() {
		
				
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		selectRequerido=0;
		select360=0;
		selectPonderado=0;
		
		View rootView = inflater.inflate(R.layout.reporte360,
				container, false);
		
		
		spinnerArea = (Spinner) rootView.findViewById(R.id.reporte360spinner);
		lista = new ArrayList<String>();
		ObtenerListaAreas();
		
		botonRequerido = (ToggleButton) rootView.findViewById(R.id.toggleButton1);
		boton360 = (ToggleButton) rootView.findViewById(R.id.toggleButton2);
		botonPonderado = (ToggleButton) rootView.findViewById(R.id.toggleButton3);
		
		btnSubmit = (Button) rootView.findViewById(R.id.reporte360btnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
		 		    
			    if (botonRequerido.isChecked()) selectRequerido=1;
			    if (boton360.isChecked()) select360=1;
			    if (botonPonderado.isChecked()) selectPonderado=1;
			    
			    if (!botonRequerido.isChecked() && !boton360.isChecked()  && !botonPonderado.isChecked()){
			    
			    Toast.makeText(v.getContext(),
						"Selecciona al menos una de las opciones ", 
							Toast.LENGTH_SHORT).show();
			    	
			    }
			    else {
			    
			      Reporte360Grafico fragment = new  Reporte360Grafico();
			      
			      Bundle argumentos = new Bundle();
			     // argumentos.putInt("AreaSelec", periodoSelec);
			      argumentos.putString("titulo", titulo);
			      argumentos.putInt("Select1", selectRequerido);
			      argumentos.putInt("Select2", select360);
			      argumentos.putInt("Select3", selectPonderado);			      
			      fragment.setArguments(argumentos);
			      
				  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				  ft.replace(R.id.opcion_detail_container, fragment);
				  ft.addToBackStack(null);
				  ft.commit();
			    }
				  
			  }
		 
			});
		
		return rootView;
		
	}
	
	void ObtenerListaAreas (){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = ReporteServices.obtenerPeriodos;
			//String request = Servicio.LoginService;
			metodoTemporal();
			//new getPeriodos().execute(request);
			
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
	
	void metodoTemporal(){
		lista =new ArrayList<String>();
		lista.add("Area 1");
		lista.add("Area 2");
		lista.add("Area 3");		
		
		ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArea.setAdapter(dataAdapter);
		spinnerArea.setOnItemSelectedListener(new OnItemSelectedListener(){
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				
			/*	Toast.makeText(parent.getContext(), 
					"seleccionado : " + parent.getItemAtPosition(pos).toString() + " id: " + listaPeriodos.get(pos).getID(),
					Toast.LENGTH_SHORT).show(); */
				
				//periodoSelec = listaPeriodos.get(pos).getID(); //aqui idobjetivo selec
				titulo = parent.getItemAtPosition(pos).toString();

			  }
			
		
			@Override
			  public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			  }
			
		});
		
		
	}

}
