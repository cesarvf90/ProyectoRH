package pe.edu.pucp.proyectorh.lineadecarrera;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.lineadecarrera.CandidatosxPuesto.OfertaLaboralMobileJefeDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.CandidatosxPuesto.FuncionDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.CandidatosxPuesto.PostulanteConCompetenciasDTO;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.AlertDialog;
import android.content.Context;
import android.webkit.WebView;

public class ListaCandidatosxPuesto extends Fragment{

	int idConvocatoriaC;
	int idColaborador;
	String NomColaborador;
	int match;
	private Spinner spinnerRequisitos;
	private Spinner spinnerCandidatos;
	private Button btnSubmit;
	
	private List<OfertaLaboralMobileJefeDTO> listaConvC;
	private List<String> listaNC;
	private List<String> listaCand;
	public List<FuncionDTO> listaReqC;
	public List<PostulanteConCompetenciasDTO> listaPost;
	
	//String[] listaCand = new String[]{"Juan Perez", "Hans Espinoza", "Christian Perez", "Andre Montoya"};
	
	public ListaCandidatosxPuesto(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.candidatosxpuesto2lista,
				container, false);
		
		idConvocatoriaC = getArguments().getInt("ConvSelec");
		spinnerRequisitos = (Spinner) rootView.findViewById(R.id.cand_x_puesto_requi);
		spinnerCandidatos = (Spinner) rootView.findViewById(R.id.lista_cand_x_puesto);
		listaNC = new ArrayList<String>();
		listaCand = new ArrayList<String>();
		
		String tituloconvc = getArguments().getString("titulo");
		String descripc = getArguments().getString("desc");
		//String usuario = getArguments().getString("IdUsuario");
		//listaCand = new ArrayList<String>();
		
		TextView textView = (TextView)rootView.findViewById(R.id.CandxPuestoConvSelec);
		textView.setText(tituloconvc);
		
		TextView textView2 = (TextView)rootView.findViewById(R.id.cand_x_puesto_descrip);
		textView2.setText(descripc);
		
		//obtenerlistaCandidatos();
		
		//obtenerlistaRequisitos();
		//Mono:
		Sesion objetoSesion = new Sesion();
		List<OfertaLaboralMobileJefeDTO> Convocatorias = objetoSesion.getConvocatorias();
		
		listaConvC = Convocatorias;
		for(int i =0; i<listaConvC.size();i++){
			
			if (listaConvC.get(i).getID() == idConvocatoriaC){
				
				listaReqC = listaConvC.get(i).getFunciones();
				for (int j=0; j<listaReqC.size();j++){
					listaNC.add(listaReqC.get(j).getNombre());
				}
				
				listaPost = listaConvC.get(i).getPostulantesConCompetencias();
				for (int j=0; j<listaPost.size();j++){
					listaCand.add(listaPost.get(j).getNombre());
				}
			}
			
		}
		
		ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaNC);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerRequisitos.setAdapter(dataAdapter);
		
		ArrayAdapter dataAdapter2 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaCand);
		dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCandidatos.setAdapter(dataAdapter2);
		
		spinnerCandidatos.setOnItemSelectedListener(new OnItemSelectedListener(){
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				
				idColaborador = listaPost.get(pos).getIdPostulante();
				NomColaborador = listaPost.get(pos).getNombre();
				match = listaPost.get(pos).getMatchLevel();
			}
			
			@Override
			  public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			  }
		});
		
		btnSubmit = (Button) rootView.findViewById(R.id.ColaboradorSelecbtnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			  public void onClick(View v) {
				
				GraficoMatchCandidatosxPuesto fragment = new GraficoMatchCandidatosxPuesto();
				
				Bundle argumentos = new Bundle();
				argumentos.putInt("ColaboradorID", idColaborador);
				argumentos.putString("ColaboradorNom", NomColaborador);
				argumentos.putInt("MatchLevel", match);
				argumentos.putInt("ConvocatoriaID",idConvocatoriaC);
				fragment.setArguments(argumentos);
				
				FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.opcion_detail_container, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
			
		});
		
		return rootView;
	}
	
	/*protected void obtenerlistaRequisitos(){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			  String request = LineaCarServices.obtenerConvocatorias;
			  new getRequisitos().execute(request);
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
		}*/
	
	/*public class getRequisitos extends AsyncCall {
		
		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			List<OfertaLaboralMobilePostulanteDTO> Convocatorias = gson.fromJson(result,
					new TypeToken<List<OfertaLaboralMobilePostulanteDTO>>(){}.getType());
			
			listaConvC = Convocatorias;
			
			for(int i =0; i<listaConvC.size();i++){
				
				if (listaConvC.get(i).getID() == idConvocatoriaC){
					listaReqC = listaConvC.get(i).getFunciones(); 
					for (int j=0; j<listaReqC.size();j++){
						listaNC.add(listaReqC.get(j).getNombre());
					}	
				}
			}
			
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaNC);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerRequisitos.setAdapter(dataAdapter);
		}
	}*/
	
}
