package pe.edu.pucp.proyectorh.evaluacion360;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Evaluador;
import pe.edu.pucp.proyectorh.model.Proceso;
import pe.edu.pucp.proyectorh.model.ProcesoEvaluacion360;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.AdaptadorDeEvaluaciones;
import pe.edu.pucp.proyectorh.utils.AdaptadorDeObjetivos;
import pe.edu.pucp.proyectorh.utils.EvaluacionesAdaptador;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MisSubordinados extends Fragment {
	
	private Map<ProcesoEvaluacion360, ArrayList<ProcesoXEvaluadorXEvaluado>> datos;
	private Object[] losProcesos;
	private View rootView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View elLadoDerecho = inflater.inflate(R.layout.subordinados_360, container, false);
		rootView = elLadoDerecho;
		
		Context contexto = elLadoDerecho.getContext();
		
		Spinner elProcesoDeLaLista = (Spinner) elLadoDerecho.findViewById(R.id.elNombre);
		
		ArrayAdapter losDatos = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, Proceso.entregarListado());
		losDatos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		elProcesoDeLaLista.setAdapter(losDatos);
		
		preparaLaSupervisionDePruebas(elLadoDerecho);
		
		elProcesoDeLaLista.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				ProcesoEvaluacion360 elProceso = (ProcesoEvaluacion360) losProcesos[pos];
				ExpandableListView losSubordinados = (ExpandableListView) rootView.findViewById(R.id.laSupervisionDeEvaluaciones);
				
				ArrayList<ProcesoXEvaluadorXEvaluado> pxexes = getDatos().get(elProceso);
				
				AdaptadorDeEvaluaciones adaptador = new AdaptadorDeEvaluaciones(getActivity().getApplicationContext(), pxexes);
				
				losSubordinados.setAdapter(adaptador);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		cargarDatosDelServidor();
		
		return elLadoDerecho;
	}		
	
	private void preparaLaSupervisionDePruebas(View laPantalla)
	{
				
		ProcesoXEvaluadorXEvaluado pxexe = new ProcesoXEvaluadorXEvaluado();
		
		Colaborador rchavez = new Colaborador();
		rchavez.setId("1");
		rchavez.setNombreCompleto("Rodrigo Chavez");
		
		Colaborador jcahuin = new Colaborador();
		jcahuin.setId("2");
		jcahuin.setNombreCompleto("Juan Cahuin");
		
		Colaborador mvega = new Colaborador();
		mvega.setId("3");
		mvega.setNombreCompleto("Miguel Vega");
		
		Colaborador hespinoza = new Colaborador();
		hespinoza.setId("4");
		hespinoza.setNombreCompleto("Hans Espinoza");
		
		ProcesoEvaluacion360 unProceso = new ProcesoEvaluacion360();
		unProceso.idProceso = 1;
		unProceso.Nombre = "Un proceso";
		
		ProcesoEvaluacion360 otroProceso = new ProcesoEvaluacion360();
		otroProceso.idProceso = 2;
		otroProceso.Nombre = "Otro proceso";
		
		String estadoPendiente = "Pendiente";
		String estadoCulminado = "Finalizado";
		
		int buenaNota = 90;
		int notaPromedio = 80;
		int necesitaReforzar = 70;
		
		pxexe.setElEvaluado(jcahuin);
		pxexe.setEvaluador(jcahuin);
		pxexe.setElProcesoEnQueParticipan(unProceso);
		pxexe.setEstado(estadoCulminado);
		pxexe.setLaCalificacion(notaPromedio);
		
		ProcesoXEvaluadorXEvaluado otraEvaluacion = new ProcesoXEvaluadorXEvaluado();
		
		otraEvaluacion.setElEvaluado(jcahuin);
		otraEvaluacion.setEvaluador(mvega);
		otraEvaluacion.setElProcesoEnQueParticipan(unProceso);
		otraEvaluacion.setEstado(estadoCulminado);
		otraEvaluacion.setLaCalificacion(notaPromedio);
		
		ProcesoXEvaluadorXEvaluado evaluacionDeOtroProceso = new ProcesoXEvaluadorXEvaluado();
		
		evaluacionDeOtroProceso.setElEvaluado(rchavez);
		evaluacionDeOtroProceso.setEvaluador(rchavez);
		evaluacionDeOtroProceso.setElProcesoEnQueParticipan(otroProceso);
		evaluacionDeOtroProceso.setEstado(estadoPendiente);
		evaluacionDeOtroProceso.setLaCalificacion(0); //Debido a que aun no se ha evaluado.
		
		ArrayList<ProcesoXEvaluadorXEvaluado> evaluacionesDeMisSubordinados = new ArrayList<ProcesoXEvaluadorXEvaluado>();
		
		evaluacionesDeMisSubordinados.add(pxexe);
		evaluacionesDeMisSubordinados.add(otraEvaluacion);
		evaluacionesDeMisSubordinados.add(evaluacionDeOtroProceso);
		
		
		
		Map<ProcesoEvaluacion360, ArrayList<ProcesoXEvaluadorXEvaluado>> mapa = new HashMap<ProcesoEvaluacion360, ArrayList<ProcesoXEvaluadorXEvaluado>>();
					
		for(ProcesoXEvaluadorXEvaluado pxexeActual : evaluacionesDeMisSubordinados)
		{
			ProcesoEvaluacion360 llave = pxexeActual.getElProcesoEnQueParticipan();
			if (mapa.get(llave) == null)
			{
				mapa.put(llave, new ArrayList<ProcesoXEvaluadorXEvaluado>());
			}
			mapa.get(llave).add(pxexeActual);
		}
		
		this.datos = mapa;
		
		Set<ProcesoEvaluacion360> losNombresDeLosGrupos = mapa.keySet();
		
		Spinner losProcesos = (Spinner) laPantalla.findViewById(R.id.elNombre);
		
		this.losProcesos = losNombresDeLosGrupos.toArray();
		ArrayAdapter laData = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, losNombresDeLosGrupos.toArray());
		laData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		losProcesos.setAdapter(laData);
				
	}
	
	public void cargarDatosDelServidor() {
		
		String direccionDeDestino = Servicio.ConsultarEvaluacionesDelEquipoDeTrabajo + "?deEsteJefe=" + LoginActivity.usuario.getID();
		
		new LlamadaAsincrona().execute(direccionDeDestino);
		
	}
	
	public class LlamadaAsincrona extends AsyncCall {
		@Override
		protected void onPostExecute(String respuesta) {
			System.out.println("Recibido: " + respuesta.toString());
			
			try {
				

				JSONObject respuestaJSON = new JSONObject(respuesta);
				
				JSONObject evaluacionesEnMisSubordinados = (JSONObject) respuestaJSON.get("data");
				
				JSONArray lasEvaluaciones = (JSONArray) evaluacionesEnMisSubordinados.get("evaluacionesEnMisSubordinados");
				
				ArrayList<ProcesoXEvaluadorXEvaluado> pxexes = new ArrayList<ProcesoXEvaluadorXEvaluado>();
				
				for (int i = 0; i < lasEvaluaciones.length(); i++) {
					
					JSONObject pxexeRespuesta = (JSONObject) lasEvaluaciones.getJSONObject(i);
					Colaborador evaluador = new Colaborador();
					Colaborador elEvaluado = new Colaborador();
					ProcesoEvaluacion360 proceso = new ProcesoEvaluacion360();
					
					//Datos del evaluador
					
					JSONObject evaluadorRespuesta = (JSONObject) pxexeRespuesta.getJSONObject("Evaluador");
					evaluador.setId(evaluadorRespuesta.getString("ID"));
					evaluador.setNombreCompleto(evaluadorRespuesta.getString("NombreCompleto"));
					
					JSONObject elEvaluadoRespuesta = (JSONObject) pxexeRespuesta.getJSONObject("Evaluado");
					elEvaluado.setId(elEvaluadoRespuesta.getString("ID"));

					elEvaluado.setNombreCompleto(elEvaluadoRespuesta.getString("NombreCompleto"));
					

					JSONObject elProcesoRespuesta = (JSONObject) pxexeRespuesta.getJSONObject("Proceso");
					proceso.idProceso = Integer.parseInt(elProcesoRespuesta.getString("ID"));
					proceso.Nombre = elProcesoRespuesta.getString("Nombre");
					
					ProcesoXEvaluadorXEvaluado pxexe = new ProcesoXEvaluadorXEvaluado();
					
					
					for(ProcesoXEvaluadorXEvaluado pxexeBusqueda : pxexes)
					{
							
							
						if (pxexeBusqueda.getElEvaluado().getId().compareTo(elEvaluado.getId()) == 0) {
							pxexe.setElEvaluado(pxexeBusqueda.getElEvaluado());
							
						}
						
						if (pxexeBusqueda.getEvaluador().getId().compareTo(evaluador.getId()) == 0) {
							pxexe.setEvaluador(pxexeBusqueda.getEvaluador());
						}
						
						if (pxexeBusqueda.getElProcesoEnQueParticipan().idProceso == proceso.idProceso) {
							pxexe.setElProcesoEnQueParticipan(pxexeBusqueda.getElProcesoEnQueParticipan());
						}
						
					}
					
					if (pxexe.getElEvaluado() == null) {
						pxexe.setElEvaluado(elEvaluado);
					}
					
					if (pxexe.getEvaluador() == null) {
						pxexe.setEvaluador(evaluador);
						
					}
					
					if (pxexe.getElProcesoEnQueParticipan() == null) {
						pxexe.setElProcesoEnQueParticipan(proceso);
						
					}
					
					
					
					pxexe.setLaCalificacion(Integer.parseInt(pxexeRespuesta.getString("Nota")));
					pxexe.setEstado(pxexeRespuesta.getString("Estado"));
				
					pxexes.add(pxexe);
					
									
				}
				
				Map<ProcesoEvaluacion360, ArrayList<ProcesoXEvaluadorXEvaluado>> mapa = new HashMap<ProcesoEvaluacion360, ArrayList<ProcesoXEvaluadorXEvaluado>>();
				
				for(ProcesoXEvaluadorXEvaluado pxexeActual : /*evaluacionesDeMisSubordinados*/ pxexes)
				{
					ProcesoEvaluacion360 llave = pxexeActual.getElProcesoEnQueParticipan();
					if (mapa.get(llave) == null)
					{
						mapa.put(llave, new ArrayList<ProcesoXEvaluadorXEvaluado>());
					}
					mapa.get(llave).add(pxexeActual);
				}
				
				setDatos(mapa);
				
				Set<ProcesoEvaluacion360> losNombresDeLosGrupos = mapa.keySet();
				
				Spinner losProcesos = (Spinner) /*laPantalla*/getRootView().findViewById(R.id.elNombre);
				
				setLosProcesos(losNombresDeLosGrupos.toArray());
				ArrayAdapter laData = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, losNombresDeLosGrupos.toArray());
				laData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				losProcesos.setAdapter(laData);

				
			} catch (Exception excepcion) {
				System.out.println("Ocurrio un error: " + excepcion);
				
			}
			
		}
	}

	public Map<ProcesoEvaluacion360, ArrayList<ProcesoXEvaluadorXEvaluado>> getDatos() {
		return datos;
	}

	public void setDatos(
			Map<ProcesoEvaluacion360, ArrayList<ProcesoXEvaluadorXEvaluado>> datos) {
		this.datos = datos;
	}

	public Object[] getLosProcesos() {
		return losProcesos;
	}

	public void setLosProcesos(Object[] losProcesos) {
		this.losProcesos = losProcesos;
	}

	public View getRootView() {
		return rootView;
	}

	public void setRootView(View rootView) {
		this.rootView = rootView;
	}	
	
	

}
