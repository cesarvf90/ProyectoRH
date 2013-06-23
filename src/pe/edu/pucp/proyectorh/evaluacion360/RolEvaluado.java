package pe.edu.pucp.proyectorh.evaluacion360;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.MisEvaluacionesAdaptador;
import pe.edu.pucp.proyectorh.model.Competencia;
import pe.edu.pucp.proyectorh.model.ProcesoEvaluacion360;
import pe.edu.pucp.proyectorh.model.ProcesoXCompetencias;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class RolEvaluado extends Fragment {

	private View rootView;
	
	public RolEvaluado() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
//		View laPantalla = inflater.inflate(R.layout.rol_evaluado, container, false);
//		View rootView = inflater.inflate(R.layout.rol_evaluado, container, false);
		this.rootView = inflater.inflate(R.layout.rol_evaluado, container, false);
		
		cargarDatosDelServidor();
		
//		ExpandableListView losResultados = (ExpandableListView) laPantalla.findViewById(R.id.examenes);
		
//		MisEvaluacionesAdaptador interfaz = new MisEvaluacionesAdaptador(container.getContext());
		
//		losResultados.setAdapter(interfaz);
		
//		return laPantalla;
		
		return rootView;
	}
	
	public void cargarDatosDelServidor() {
		
		String direccionDeDestino = Servicio.ConsultarNotasDeProcesoDeEvaluaciones + "?deEsteColaborador=" + LoginActivity.usuario.getID();
		
		new LlamadaAlServidor().execute(direccionDeDestino);
	}
	
	public class LlamadaAlServidor extends AsyncCall {
		@Override
		protected void onPostExecute(String respuesta) {
			System.out.println("Recibido: " + respuesta.toString());
			
			try {
				
				ArrayList<ProcesoXCompetencias> losProcesos = new ArrayList<ProcesoXCompetencias>();
				
//				JSONObject respuestaJSON =
				
				JSONObject respuestaJSON = new JSONObject(respuesta);
				
//				JSONObject laData = respuestaJSON.getJSONObject("data");
				
				JSONObject laData = (JSONObject) respuestaJSON.get("data");
				
//				JSONObject 
				
				JSONArray conjuntoProcesos = (JSONArray) laData.get("susProcesos");
				
				for (int i = 0; i < conjuntoProcesos.length(); i++) {
					
					ProcesoXCompetencias pxcs = new ProcesoXCompetencias();
					
					ProcesoEvaluacion360 proceso = new ProcesoEvaluacion360();
					
//					proceso.
					JSONObject procesoXCompetenciasRespuesta = (JSONObject) conjuntoProcesos.get(i);
					
//					JSONObject procesoRespuesta = procesoXCompetenciasRespuesta.
					
					JSONObject procesoRespuesta = (JSONObject) procesoXCompetenciasRespuesta.get("Proceso");
					
//					proceso.ID = procesoRespuesta.
					
					proceso.ID = Integer.parseInt(procesoRespuesta.getString("ID"));
					proceso.Nombre = procesoRespuesta.getString("Nombre");
					
//					JSONArray lasCompetencias = (JSONArray) /*procesoRespuesta*/.get("ResultadosCompetencias");
					JSONArray lasCompetencias = (JSONArray) procesoXCompetenciasRespuesta.get("ResultadosCompetencias");
					
					ArrayList<Competencia> competenciasModelo = new ArrayList<Competencia>();
					
//					for (int j = 0; j <)
					
					for (int j = 0; j < lasCompetencias.length(); j++) {
						
						JSONObject competenciaRespuesta = (JSONObject) lasCompetencias.get(j);
						
						Competencia unaCompetencia = new Competencia();
						
//						unaCompetencia.setID(competenciaRespuesta.getString("ID"));
//						unaCompetencia.setDescripcion(compentenciaRespuesta.getString("Descripcion"));
//						unaCompetencia.setNota(compen);
						
						unaCompetencia.setID(competenciaRespuesta.getString("ID"));
						unaCompetencia.setDescripcion(competenciaRespuesta.getString("Descripcion"));
						unaCompetencia.setNota(competenciaRespuesta.getString("Nota"));
						
//						lasCompetencias.add(unaCompetencia);
//						lasCompetenciasModelo.
						competenciasModelo.add(unaCompetencia);
						
					}
					
//					pxcs.setUnProceso(proceso);
//					pxcs.setCompetencias(competencias);
//					losProcesos.add(pxcs);
					
					pxcs.setUnProceso(proceso);
					pxcs.setCompetencias(competenciasModelo);
					
					losProcesos.add(pxcs);
				}
				
//				ExpandableListView procesosUsuario = (ExpandableListView) rootView.findViewById(R.id.examenes);
				ExpandableListView procesosUsuario = (ExpandableListView) getRootView().findViewById(R.id.examenes);
				
				MisEvaluacionesAdaptador interfaz = new MisEvaluacionesAdaptador(getActivity().getApplicationContext(), losProcesos);
				
				procesosUsuario.setAdapter(interfaz);				

			} catch (Exception error) {
				
				
			}
			
//			MisEvaluacionesAdaptador interfaz = new MisEvaluacionesAdaptador(getActivity().getApplicationContext(), losProcesos);
			
			
//			ExpandableListView procesosUsuario = (ExpandableListView) rootView.findViewById(R.id.examenes);
//			
//			MisEvaluacionesAdaptador interfaz = new MisEvaluacionesAdaptador(getActivity().getApplicationContext(), losProcesos);
//			
//			procesosUsuario.setAdapter(interfaz);
		}
		
		
		
	}

	public View getRootView() {
		return rootView;
	}

	public void setRootView(View rootView) {
		this.rootView = rootView;
	}	
	
	
}
