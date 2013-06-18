package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.services.AsyncCall;
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

public class ReporteCubrimientoPrincipal extends Fragment {
	
	private Spinner spinnerPuesto;
	private Spinner spinnerArea;
	private Button btnSubmit;
	List<String> lista ;
	
	int puestoSelec;
	String titulo;
	
	List<PuestoDTO> puestos;
	
	private Button btnDesde;
	private Button btnHasta;
	TextView txtdesde;
	TextView txthasta;
	
	int anho;
	int mes;
	int dia;
	int anhofin;
	int mesfin;
	int diafin;

	
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
		lista = new ArrayList<String>();

		obtenerlistaPuestos();
		
		//fecha de hoy
		Calendar c = Calendar.getInstance();
		anho = anhofin = c.get(Calendar.YEAR);
		mes = mesfin = c.get(Calendar.MONTH);
		dia = diafin = c.get(Calendar.DAY_OF_MONTH);

		
		
		txtdesde = (TextView) rootView.findViewById(R.id.reportecubdesde); 
		txtdesde.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(dia).append("/").append(mes + 1).append("/")
		.append(anho).append(" "));
		txthasta = (TextView) rootView.findViewById(R.id.reportecubhasta);
		txthasta.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(dia).append("/").append(mes + 1).append("/")
		.append(anho).append(" "));
		
		btnDesde = (Button) rootView.findViewById(R.id.reportecubbtndesde);
		btnHasta = (Button) rootView.findViewById(R.id.reportecubbtnhasta);
		
		/*datepickers*/
		btnDesde.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  DialogFragment newFragment = new DatePickerFragment();
				   newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
			  }
		});
		
		btnHasta.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  DatePickerFragmentFin newFragment = new DatePickerFragmentFin();
				   newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
			  }
		});
		
		

		btnSubmit = (Button) rootView.findViewById(R.id.reportecubbtnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
				  ReporteCubrimientoGrafico fragment = new ReporteCubrimientoGrafico();
				  Bundle argumentos = new Bundle();
			      argumentos.putInt("PuestoSelec", puestoSelec);
			      argumentos.putString("titulo", titulo);
			      
			      /*fechaini*/
			      String fechadesde = "" + dia;
			      if (mes<10) fechadesde = fechadesde + "0" + mes + anho;
			      else fechadesde  = fechadesde + mes + anho;
			      
			      /*fechafin*/
			      String fechahasta = "" + diafin;
			      if (mesfin<10) fechahasta = fechahasta + "0" + mesfin + anhofin;
			      else fechahasta  = fechahasta + mesfin + anhofin;
			      
			      argumentos.putString("fechaini", fechadesde);
			      argumentos.putString("fechafin", fechahasta);
			      fragment.setArguments(argumentos);
			      
				  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				  ft.replace(R.id.opcion_detail_container, fragment);
				  ft.addToBackStack(null);
				  ft.commit();
				  
				  
				  
			  }
		});
		
		customizarEstilos(getActivity(), rootView);
		return rootView;
	}
	
	
	private class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			return new DatePickerDialog(getActivity(), this, anho, (mes-1), dia);
		}
		
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			anho = selectedYear;
			mes = selectedMonth +1;
			dia = selectedDay;
			
			// set selected date into textview
			txtdesde.setText(new StringBuilder().append(dia)
			   .append("/").append(mes).append("/").append(anho)
			   .append(" "));

 
		}
	}
	
	private class DatePickerFragmentFin extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			return new DatePickerDialog(getActivity(), this, anhofin, (mesfin-1), diafin);
		}
		
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			anhofin = selectedYear;
			mesfin = selectedMonth +1;
			diafin = selectedDay;
			
			// set selected date into textview
			txthasta.setText(new StringBuilder().append(diafin)
			   .append("/").append(mesfin).append("/").append(anhofin)
			   .append(" "));

 
		}
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
