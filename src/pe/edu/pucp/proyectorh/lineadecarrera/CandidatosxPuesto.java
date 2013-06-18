package pe.edu.pucp.proyectorh.lineadecarrera;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.CompetenciaConPonderadoDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.FuncionDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.OfertaLaboralMobilePostulanteDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.getConvocatorias;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.AlertDialog;
import android.graphics.Typeface;
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
import android.widget.TextView;
import pe.edu.pucp.proyectorh.LoginActivity;

public class CandidatosxPuesto extends Fragment {
	
	private ProgressBar pbarra;
	private Spinner spinnerConvocatoria;
	private Button btnSubmit;
	private String usuario;
	
	List<String> listaC;
	List<OfertaLaboralMobileJefeDTO> listaConvC;
	
	int ConvSelecC;
	String tituloC;
	String descC;
	
	public CandidatosxPuesto(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.candidatosxpuesto1,
				container, false);
		
		TextView txt = (TextView) rootView.findViewById(R.id.candidatosxpuesto1titulo);  
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");  
		txt.setTypeface(font);
		
		pbarra = (ProgressBar) rootView.findViewById(R.id.candxpuestoprogressbar);
		spinnerConvocatoria = (Spinner) rootView.findViewById(R.id.candxpuestospinner);
		listaC = new ArrayList<String>();
		usuario = LoginActivity.usuario.getUsername();
		obtenerlistaConvocatoriasCxP(usuario);
		btnSubmit = (Button) rootView.findViewById(R.id.candxpuestobtnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			  public void onClick(View v) {
				
					  ListaCandidatosxPuesto fragment = new ListaCandidatosxPuesto();
					
					  Bundle argumentos = new Bundle();
				      argumentos.putInt("ConvSelec", ConvSelecC);
				      argumentos.putString("titulo", tituloC);
				      //argumentos.putString("IdUsuario", usuario);
				      argumentos.putString("desc", descC);
				      //argumentos.putDouble("match",match);
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
	
	protected void obtenerlistaConvocatoriasCxP(String usuario){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			  String request = LineaCarServices.obtenerConvXCandidato + "?userName=" + usuario;
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
		
		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			
			//Mono:
			List<OfertaLaboralMobileJefeDTO> Convocatorias = gson.fromJson(result,
					new TypeToken<List<OfertaLaboralMobileJefeDTO>>(){}.getType());
			Sesion objetoSesion = new Sesion();
			objetoSesion.setConvocatorias(Convocatorias);
			
			listaConvC = Convocatorias;
			
			for(int i =0; i<listaConvC.size();i++){
				listaC.add(listaConvC.get(i).getNombreAreaPuesto());
			}
			
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaC);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerConvocatoria.setAdapter(dataAdapter);
			
			spinnerConvocatoria.setOnItemSelectedListener(new OnItemSelectedListener(){
				
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					
					ConvSelecC = listaConvC.get(pos).getID(); //aqui idconvocatoria selec
					tituloC = parent.getItemAtPosition(pos).toString();
					descC = listaConvC.get(pos).getDescripcionOferta().toString();
				}
				
				@Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
			});
		}
	}

	public class OfertaLaboralMobileJefeDTO {
		
		private int ID;
		private String NombreAreaPuesto;
		private String DescripcionOferta;
		private int SueldoTentativo;
		private String FechaFinVigencia;
		private List<FuncionDTO> Funciones;
		private List<CompetenciaConPonderadoDTO> CompetenciasPonderadasPuesto;
		private List<PostulanteConCompetenciasDTO> PostulantesConCompetencias;
		
		public OfertaLaboralMobileJefeDTO() {
		}
		
		public int getID() {
			return ID;
		}


		public void setID(int iD) {
			ID = iD;
		}
		
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
		
		public int getSueldoTentativo() {
			return SueldoTentativo;
		}
		
		public List<FuncionDTO> getFunciones() {
			return Funciones;
		}


		public void setFunciones(List<FuncionDTO> funciones) {
			Funciones = funciones;
		}


		public void setSueltoTentativo(int sueldotent) {
			SueldoTentativo = sueldotent;
		}
		
		public List<CompetenciaConPonderadoDTO> getCompetenciasPonderadasPuesto() {
			return CompetenciasPonderadasPuesto;
		}


		public void setCompetenciasPonderadasPuesto(List<CompetenciaConPonderadoDTO> competenciasponderadaspuesto) {
			CompetenciasPonderadasPuesto = competenciasponderadaspuesto;
		}
		
		public List<PostulanteConCompetenciasDTO> getPostulantesConCompetencias() {
			return PostulantesConCompetencias;
		}


		public void setPostulantesConCompetencias(List<PostulanteConCompetenciasDTO> postulantesconcompetencias) {
			PostulantesConCompetencias = postulantesconcompetencias;
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
	
	public class PostulanteConCompetenciasDTO{
		
		private int IdPostulante;
		private String Nombre;
		private List<CompetenciaConPonderadoDTO> CompetenciasPostulante;
		private int MatchLevel;
		
		public int getIdPostulante() {
			return IdPostulante;
		}

		public void setIdPostulante(int idpostulante) {
			IdPostulante = idpostulante;
		}
		
		public String getNombre() {
			return Nombre;
		}

		public void setNombre(String nombre) {
			Nombre = nombre;
		}
		
		public List<CompetenciaConPonderadoDTO> getCompetenciasPostulante() {
			return CompetenciasPostulante;
		}


		public void setCompetenciasPostulante(List<CompetenciaConPonderadoDTO> competenciaspostulante) {
			CompetenciasPostulante = competenciaspostulante;
		}
		
		public int getMatchLevel() {
			return MatchLevel;
		}

		public void setMatchLevel(int matchlevel) {
			MatchLevel = matchlevel;
		}
	}
}
