package pe.edu.pucp.proyectorh.evaluacion360;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.evaluacion360.RolEvaluado.LlamadaAlServidor;
import pe.edu.pucp.proyectorh.model.*;
import pe.edu.pucp.proyectorh.objetivos.AvanceExpandableAdapter;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.MisEvaluacionesAdaptador;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;


public class RolEvaluadoEver extends Fragment {
	View rootView;
	FragmentActivity actv;
	Context contexto;
	
	ExpandableListView listaProcesos;
	EvaluadoExpandableAdapter adapter;
	
	private Spinner spinnerPeriodo;
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	List<String> listaNombrePer;
	int periodoBSCActual;	
	TableLayout lay;
	
	public RolEvaluadoEver(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	
	public void listarProcesos(){
		String rutaLlamada = Servicio.ConsultarNotasDeProcesoDeEvaluaciones + "?deEsteColaborador=" + LoginActivity.usuario.getID();
		ListadoProcesos lp = new ListadoProcesos();
		Servicio.llamadaServicio(this.getActivity(), lp,rutaLlamada);
	}

	public class ListadoProcesos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			
			try {
				
				ArrayList<ProcesoXCompetencias> losProcesos = new ArrayList<ProcesoXCompetencias>();
				
				JSONObject respuestaJSON = new JSONObject(result);
				
				JSONObject laData = (JSONObject) respuestaJSON.get("data");
				
				JSONArray conjuntoProcesos = (JSONArray) laData.get("susProcesos");
				
				for (int i = 0; i < conjuntoProcesos.length(); i++) {
					
					ProcesoXCompetencias pxcs = new ProcesoXCompetencias();
					
					ProcesoEvaluacion360 proceso = new ProcesoEvaluacion360();
					
					JSONObject procesoXCompetenciasRespuesta = (JSONObject) conjuntoProcesos.get(i);
					
					JSONObject procesoRespuesta = (JSONObject) procesoXCompetenciasRespuesta.get("Proceso");
					
					proceso.ID = Integer.parseInt(procesoRespuesta.getString("ID"));
					proceso.Nombre = procesoRespuesta.getString("Nombre");
				
					JSONArray lasCompetencias = (JSONArray) procesoXCompetenciasRespuesta.get("ResultadosCompetencias");
					
					ArrayList<Competencia> competenciasModelo = new ArrayList<Competencia>();
									
					for (int j = 0; j < lasCompetencias.length(); j++) {
						
						JSONObject competenciaRespuesta = (JSONObject) lasCompetencias.get(j);
						
						Competencia unaCompetencia = new Competencia();
						
						unaCompetencia.setID(competenciaRespuesta.getString("ID"));
						unaCompetencia.setDescripcion(competenciaRespuesta.getString("Descripcion"));
						unaCompetencia.setNota(competenciaRespuesta.getString("Nota"));
						
						competenciasModelo.add(unaCompetencia);
						
					}
					
					pxcs.setUnProceso(proceso);
					pxcs.setCompetencias(competenciasModelo);
					
					losProcesos.add(pxcs);
				}
				
				
				adapter = new EvaluadoExpandableAdapter(contexto, losProcesos);
				listaProcesos.setAdapter(adapter);

			} catch (Exception e) {
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView  = inflater.inflate(R.layout.rol_evaluado,container, false);
		actv = getActivity();
		contexto = rootView.getContext();
		rootView.findViewById(R.layout.rol_evaluado);
		
		Resources res = getResources();
		
		listaProcesos = (ExpandableListView) rootView.findViewById(R.id.examenes);
		listaProcesos.setLongClickable(true);
		

		// Se muestra la informacion de la oferta
		listaProcesos.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		});

		// Se muestra la informacion de el postulante
		listaProcesos.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
					return false;
			}
		});

		// Se dirige a la evaluacion del postulante
		listaProcesos.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,int position, long id) {
				return false;
			}
		});
		
		listarProcesos();
		
		return rootView;
	}

}