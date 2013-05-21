package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;


import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.Area;
import pe.edu.pucp.proyectorh.model.OfertaLaboral;
import pe.edu.pucp.proyectorh.model.Periodo;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import pe.edu.pucp.proyectorh.model.Postulante;
import pe.edu.pucp.proyectorh.model.Puesto;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa.ListadoObjetivos;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa.ListadoPeriodos;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa.TableFila;
import pe.edu.pucp.proyectorh.reclutamiento.EvaluacionPostulanteFragment;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.OfertasAdapter;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MisObjetivos extends Fragment {
	
	ArrayList<ObjetivosBSC> objsPadre;
	ArrayList<ObjetivosBSC> objsHijos;
	
	private Spinner spinnerPeriodo;
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	List<String> listaNombrePer;
	int periodoBSCActual;
	
	public MisObjetivos(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView  = inflater.inflate(R.layout.listar_objetivos,container, false);
			Context contexto = rootView.getContext();
			rootView.findViewById(R.layout.listar_objetivos);
			
			Resources res = getResources();

			
			/*
			 * CODIGO PARA MANEJO DE PERIODO (SPINNER)
			 */
			spinnerPeriodo = (Spinner) rootView.findViewById(R.id.spinnerMisObjsPeriodo);
			listaNombrePer = new ArrayList<String>();
			ListadoPeriodos lp = new ListadoPeriodos();
			Servicio.llamadaServicio(this.getActivity(), lp,Servicio.ListarPeriodos);

			
			ExpandableListView listaObjs = (ExpandableListView) rootView.findViewById(R.id.listaObjetivos);
		//	OfertasAdapter adapter = new OfertasAdapter(this.getActivity().getApplicationContext(), ofertas, postulantes);*/
		//	listaObjs.setAdapter(adapter);
			listaObjs.setLongClickable(true);

			// Se muestra la informacion de la oferta
			listaObjs.setOnGroupClickListener(new OnGroupClickListener() {
				@Override
				public boolean onGroupClick(ExpandableListView parent, View v,
						int groupPosition, long id) {
				/*	System.out.println("Grupo " + (groupPosition));
					if (groupPosition != ofertaSeleccionadaPosicion) {
						mostrarOfertaSeleccionada(ofertasList.get(groupPosition));
						mostrarPostulanteVacio();
						ofertaSeleccionadaPosicion = groupPosition;
					}*/
					return false;
				}
			});

			// Se muestra la informacion de el postulante
			listaObjs.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
			/*		if (groupPosition != ofertaSeleccionadaPosicion) {
						// Si se selecciono un postulante de una oferta distinta a
						// la que se esta mostrando se refresca tambien el detalle
						// de la oferta
						mostrarOfertaSeleccionada(ofertasList.get(groupPosition));
						mostrarPostulanteSeleccionado(ofertasList
								.get(groupPosition).getPostulantes()
								.get(childPosition));
						postulanteSeleccionadoPosicion = childPosition;
						ofertaSeleccionadaPosicion = groupPosition;
					} else if ((childPosition != postulanteSeleccionadoPosicion)
							&& (groupPosition == ofertaSeleccionadaPosicion)) {
						// Si se selecciono un postulante de la misma oferta que se
						// esta mostrando solo se refresca el detalle del postulante
						mostrarPostulanteSeleccionado(ofertasList
								.get(groupPosition).getPostulantes()
								.get(childPosition));
						postulanteSeleccionadoPosicion = childPosition;
					}*/
					return false;
				}
			});

			// Se dirige a la evaluacion del postulante
			listaObjs.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View view,int position, long id) {
					/*AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Evaluar postulante");
					builder.setMessage("¿Desea realizar la evaluación de entrevista final para este postulante?");
					builder.setCancelable(false);
					builder.setCancelable(false);
					builder.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					builder.setPositiveButton("Evaluar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									EvaluacionPostulanteFragment fragment = new EvaluacionPostulanteFragment();
									getActivity()
											.getSupportFragmentManager()
											.beginTransaction()
											.replace(R.id.opcion_detail_container,
													fragment).commit();
								}
							});
					builder.create();
					builder.show();*/
					return false;
				}
			});
		return rootView;
	}
	
	
	public void cargaObjs(){
		objsPadre = new ArrayList<ObjetivosBSC>();
		objsHijos = new ArrayList<ObjetivosBSC>();
	}
	
	public  void listarObjetivos(){
		ListadoObjetivos lo = new ListadoObjetivos();
		String rutaLlamada = Servicio.ListarObjetivosBSC+"?tipoObjetivoBSCID=1&BSCID="+periodoBSCActual;
		System.out.println("EMF-ruta1="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
		rutaLlamada = Servicio.ListarObjetivosBSC+"?tipoObjetivoBSCID=2&BSCID="+periodoBSCActual;
		System.out.println("EMF-ruta2="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
		rutaLlamada = Servicio.ListarObjetivosBSC+"?tipoObjetivoBSCID=3&BSCID="+periodoBSCActual;
		System.out.println("EMF-ruta3="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
		rutaLlamada = Servicio.ListarObjetivosBSC+"?tipoObjetivoBSCID=4&BSCID="+periodoBSCActual;
		System.out.println("EMF-ruta4="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
	}
	
	public class ListadoObjetivos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			try {
				JSONArray array = new JSONArray(result);
				ArrayList<ObjetivosBSC> listObjetivosBSC = new ArrayList<ObjetivosBSC>();
				for(int i = 0; i < array.length(); i++) {
					final Gson gson = new Gson();
					final ObjetivosBSC oBSC = gson.fromJson(array.getString(i),ObjetivosBSC.class);
					listObjetivosBSC.add(oBSC);
				}
				
				//FILAS
				for(int i=0;i<listObjetivosBSC.size();i++){

					
				}
			} catch (Exception e){
				System.out.println("Error="+e.toString());
			}
		}
	}
	

	
	public class ListadoPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			listaPeriodos = new ArrayList<Periodo>();
			try {
				JSONArray arregloPeriodos = new JSONArray(result);
				for(int i=0;i<arregloPeriodos.length();i++){
					JSONObject periodoJSON = arregloPeriodos.getJSONObject(i);
					Periodo per = new Periodo(periodoJSON.getString("Nombre"),periodoJSON.getInt("BSCID"));
					listaPeriodos.add(per);
				}
				for(int i=0; i<listaPeriodos.size(); i++){
					listaNombrePer.add(listaPeriodos.get(i).Nombre);	
				}
				
				ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,listaNombrePer);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerPeriodo.setAdapter(dataAdapter);
				
				spinnerPeriodo.setOnItemSelectedListener(new OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
						periodoBSCActual = listaPeriodos.get(pos).BSCID;
						System.out.println("periodo seleccionado="+periodoBSCActual);
						//EMF-//actualizaTabs();
					}
				
					@Override
					  public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					  }
				});
			} catch (Exception e){
				System.out.println("Error="+e.toString());
			}
		}
	}

}
