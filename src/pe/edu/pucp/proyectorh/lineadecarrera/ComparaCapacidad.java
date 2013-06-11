package pe.edu.pucp.proyectorh.lineadecarrera;

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
import pe.edu.pucp.proyectorh.LoginActivity;

public class ComparaCapacidad extends Fragment{

	private Spinner spinnerConvocatoria;
	private Button btnSubmit;
	private ProgressBar pbarra;
	private String usuario;
	
	int ConvSelec;
	int match;
	String titulo;
	String desc;
	
	List<OfertaLaboralMobilePostulanteDTO> listaConv;
	List<String> lista ;
	
	public ComparaCapacidad(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.comparacap1principal,
				container, false);
		pbarra = (ProgressBar) rootView.findViewById(R.id.comparacapprogressbar);
		
		spinnerConvocatoria = (Spinner) rootView.findViewById(R.id.comparacapspinner);
		lista = new ArrayList<String>();
		usuario = LoginActivity.usuario.getUsername();
		obtenerlistaConvocatorias(usuario);
		
		btnSubmit = (Button) rootView.findViewById(R.id.comparacapbtnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
		 /*
			    Toast.makeText(v.getContext(),
				"Seleccionado "+ String.valueOf(spinnerPeriodo.getSelectedItem()), 
					Toast.LENGTH_SHORT).show();
			*/    
				 // obtenerlistaConvocatorias();

			      ComparaCapacidadPersonal fragment = new ComparaCapacidadPersonal();
			      
			      Bundle argumentos = new Bundle();
			      argumentos.putInt("ConvSelec", ConvSelec);
			      argumentos.putString("titulo", titulo);
			      argumentos.putString("IdUsuario", usuario);
			      argumentos.putString("desc", desc);
			      argumentos.putInt("match",match);
			      //argumentos.putString("idUsuario", idUsuario);
			      fragment.setArguments(argumentos);
			      
				  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				  ft.replace(R.id.opcion_detail_container, fragment);
				  ft.addToBackStack(null);
				  ft.commit();
				  
			  }
		 
			});
		
		return rootView;
	}
	
	protected void obtenerlistaConvocatorias(String usuario){
		
		if (ConnectionManager.connect(getActivity())) {
		// construir llamada al servicio
		  String request = LineaCarServices.obtenerConvocatorias + "?userName=" + usuario;
		  new getConvocatorias().execute(request);
		}else {
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
	
	public class getConvocatorias extends AsyncCall {
		//webservice
		
		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			List<OfertaLaboralMobilePostulanteDTO> Convocatorias = gson.fromJson(result,
					new TypeToken<List<OfertaLaboralMobilePostulanteDTO>>(){}.getType());
			
			Sesion objetoSesion = new Sesion();
			objetoSesion.setOfertas(Convocatorias);
			
			listaConv = Convocatorias;
			
			for(int i =0; i<listaConv.size();i++){
				lista.add(listaConv.get(i).getNombreAreaPuesto());
			}
			
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerConvocatoria.setAdapter(dataAdapter);
			spinnerConvocatoria.setOnItemSelectedListener(new OnItemSelectedListener(){
				
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					
				/*	Toast.makeText(parent.getContext(), 
						"seleccionado : " + parent.getItemAtPosition(pos).toString() + " id: " + listaPeriodos.get(pos).getID(),
						Toast.LENGTH_SHORT).show(); */
					
					ConvSelec = listaConv.get(pos).getID(); //aqui idconvocatoria selec
					titulo = parent.getItemAtPosition(pos).toString();
					desc = listaConv.get(pos).getDescripcionOferta().toString();
					match = listaConv.get(pos).getMatchLevel();
						
				  }
				@Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
			});
		}
	}
	
	public class OfertaLaboralMobilePostulanteDTO {
		
		private int ID ;
		private String NombreAreaPuesto;
		//private String NombrePuesto;
		//private String Nombre;
		private String DescripcionOferta;
		private int SueldoTentativo;
		private List<FuncionDTO> Funciones;
		private List<CompetenciaConPonderadoDTO> CompetenciasPonderadasPuesto;
		private List<CompetenciaConPonderadoDTO> CompetenciasPonderadasColaborador;
		private int MatchLevel;
		private String FechaFinVigencia;
		
		public OfertaLaboralMobilePostulanteDTO() {
		}
		
		public int getID() {
			return ID;
		}


		public void setID(int iD) {
			ID = iD;
		}
		
		//public String getNombre() {
			//return Nombre;
		//}


		//public void setNombre(String nombre) {
			//Nombre = nombre;
		//}
		
		public String getNombreAreaPuesto() {
			return NombreAreaPuesto;
		}


		public void setNombreAreaPuesto(String nombreareapuesto) {
			NombreAreaPuesto = nombreareapuesto;
		}
		
		public String getDescripcionOferta() {
			return DescripcionOferta;
		}


		public void setDescripcionOferta(String descoferta) {
			DescripcionOferta = descoferta;
		}
		
		public String getFechaFinVigencia() {
			return FechaFinVigencia;
		}


		public void setFechaFinVigencia(String fechafinvigencia) {
			FechaFinVigencia = fechafinvigencia;
		}
		
		public List<FuncionDTO> getFunciones() {
			return Funciones;
		}


		public void setFunciones(List<FuncionDTO> funciones) {
			Funciones = funciones;
		}
		
		public List<CompetenciaConPonderadoDTO> getCompetenciasPonderadasPuesto() {
			return CompetenciasPonderadasPuesto;
		}


		public void setCompetenciasPonderadasPuesto(List<CompetenciaConPonderadoDTO> competenciasponderadaspuesto) {
			CompetenciasPonderadasPuesto = competenciasponderadaspuesto;
		}
		
		public List<CompetenciaConPonderadoDTO> getCompetenciasPonderadasColaborador() {
			return CompetenciasPonderadasColaborador;
		}


		public void setCompetenciasPonderadasColaborador(List<CompetenciaConPonderadoDTO> competenciasponderadascolaborador) {
			CompetenciasPonderadasColaborador = competenciasponderadascolaborador;
		}
		
		public int getSueldoTentativo() {
			return SueldoTentativo;
		}


		public void setSueltoTentativo(int sueldotent) {
			SueldoTentativo = sueldotent;
		}
		
		public int getMatchLevel() {
			return MatchLevel;
		}

		public void setMatchLevel(int matchlevel) {
			MatchLevel = matchlevel;
		}
	}
	
	public class FuncionDTO {
		
		private int ID;
		private String Nombre;
		private int PuestoID;
		private int Peso;
		
		public int getID() {
			return ID;
		}

		public void setID(int iD) {
			ID = iD;
		}
		
		public int getPuestoID() {
			return PuestoID;
		}

		public void setPuestoID(int iD) {
			PuestoID = iD;
		}
		
		public String getNombre() {
			return Nombre;
		}

		public void setNombre(String nombre) {
			Nombre = nombre;
		}
		
		public int getPeso() {
			return Peso;
		}

		public void setPeso(int peso) {
			Peso = peso;
		}
		
	}
	
	public class CompetenciaConPonderadoDTO {
		
		private int CompetenciaID;
		private String CompetenciaNombre;
		private int Porcentaje;
		
		public int getCompetenciaID() {
			return CompetenciaID;
		}

		public void setCompetenciaID(int competenciaid) {
			CompetenciaID = competenciaid;
		}
		
		public String getCompetenciaNombre() {
			return CompetenciaNombre;
		}

		public void setCompetenciaNombre(String competencianombre) {
			CompetenciaNombre = competencianombre;
		}
		
		public int getPorcentaje() {
			return Porcentaje;
		}

		public void setPorcentaje(int porcentaje) {
			Porcentaje = porcentaje;
		}
	}
}
