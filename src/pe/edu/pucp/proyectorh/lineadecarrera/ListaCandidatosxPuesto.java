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
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.FuncionDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.OfertaLaboralMobilePostulanteDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidadPersonal.getRequisitos;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Context;
import android.webkit.WebView;

public class ListaCandidatosxPuesto extends Fragment{

	int idConvocatoriaC;
	private Spinner spinnerRequisitos;
	private Spinner spinnerCandidatos;
	
	List<OfertaLaboralMobilePostulanteDTO> listaConvC;
	List<String> listaNC;
	//List<String> listaCand 
	List<FuncionDTO> listaReqC;
	
	String[] listaCand = new String[]{"Juan Perez", "Hans Espinoza", "Christian Perez", "Andre Montoya"};
	
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
		//listaCand = new ArrayList<String>();
		
		
		//obtenerlistaRequisitos();
		//Mono:
		Sesion objetoSesion = new Sesion();
		List<OfertaLaboralMobileJefeDTO> Convocatorias = objetoSesion.getConvocatorias();
		
		String tituloconvc = getArguments().getString("titulo");
		String descripc = getArguments().getString("desc");
		//String usuario = getArguments().getString("IdUsuario");
		
		TextView textView = (TextView)rootView.findViewById(R.id.CandxPuestoConvSelec);
		textView.setText(tituloconvc);
		
		TextView textView2 = (TextView)rootView.findViewById(R.id.cand_x_puesto_descrip);
		textView2.setText(descripc);
		
		//obtenerlistaCandidatos();
		
		ArrayAdapter dataAdapter2 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaCand);
		dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCandidatos.setAdapter(dataAdapter2);
		
		return rootView;
	}
	
	protected void obtenerlistaRequisitos(){
		
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
		}
	
	public class getRequisitos extends AsyncCall {
		
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
	}
	
}
