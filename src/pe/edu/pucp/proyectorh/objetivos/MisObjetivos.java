package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Periodo;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

public class MisObjetivos extends Fragment {
	
	public int indicador=0;
	
	public static int IND_MISOBJS=1;
	public static int IND_SUBORD=2;	
	
	ArrayList<ObjetivosBSC> objsPadre;
	ArrayList<ObjetivosBSC> objsHijos;
	
	private Spinner spinnerPeriodo;
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	List<String> listaNombrePer;
	int periodoBSCActual;
	
	ExpandableListView listaObjs;
	
	Context contexto;
	
	private ArrayList<ObjetivosBSC> groups;
	private ArrayList<ArrayList<ObjetivosBSC>> childs;
	
	
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
			contexto = rootView.getContext();
			rootView.findViewById(R.layout.listar_objetivos);
			
			Resources res = getResources();
			
			/*
			 * CODIGO PARA MANEJO DE PERIODO (SPINNER)
			 */
			spinnerPeriodo = (Spinner) rootView.findViewById(R.id.spinnerMisObjsPeriodo);
			listaNombrePer = new ArrayList<String>();
			ListadoPeriodos lp = new ListadoPeriodos();
			Servicio.llamadaServicio(this.getActivity(), lp,Servicio.ListarPeriodos);

			
			listaObjs = (ExpandableListView) rootView.findViewById(R.id.listaObjetivos);
			System.out.println("setea a cero");
	    	groups= new ArrayList<ObjetivosBSC>();
	    	childs= new ArrayList<ArrayList<ObjetivosBSC>>();
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
	
	
    private void loadData(ArrayList<ObjetivosBSC> listObjetivosBSC){
    	for(int i=0;i<listObjetivosBSC.size();i++){
    		System.out.println("agrega obj="+listObjetivosBSC.get(i).Nombre);
    		groups.add(listObjetivosBSC.get(i));
    		
      		childs.add(new ArrayList<ObjetivosBSC>());
    		//TableFila fila = agregaFila(auxPerspectiva,objBSC,flagUltimo);
    		  		
        	for(int j=0; j<3;j++){
        	    childs.get(groups.size()-1).add(new ObjetivosBSC("prueba gg"+i));
        	}
    	}
    	
    	ListadoObjetivos lo = new ListadoObjetivos();
    	String rutaLlamada ="";

    	if(indicador==IND_MISOBJS){
    		System.out.println("MIS OBJETIVOS 2");
    		rutaLlamada = Servicio.ListarMisObjetivosSuperiores+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual;
    	}else if(indicador==IND_SUBORD){
    		System.out.println("MIS SUBORDINADOS 2");
			rutaLlamada = Servicio.ListarMisObjetivos+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
    	}
    	
    	System.out.println("Ruta-Hijos="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada); //SE LLAMA A VER MIS OBJETIVOS DEFINIDOS PARA MI

    	System.out.println("new adapter");
    	ObjetivosExpandableAdapter adapter = new ObjetivosExpandableAdapter(contexto, groups, childs);
    	listaObjs.setAdapter(adapter);
    }
    
    public boolean isAdmin(){
    	return true;
    }
	
	public  void listarObjetivos(){
    	groups= new ArrayList<ObjetivosBSC>();
    	childs= new ArrayList<ArrayList<ObjetivosBSC>>();

    	ListadoObjetivos lo = new ListadoObjetivos();
    	String rutaLlamada ="";
    	
    	if(indicador==IND_MISOBJS){
    		System.out.println("MIS OBJETIVOS");
    		rutaLlamada = Servicio.ListarMisObjetivosSuperiores+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual;
    	}else if(indicador==IND_SUBORD){
    		System.out.println("MIS SUBORDINADOS");
			rutaLlamada = Servicio.ListarMisObjetivos+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
    	}
    	
    	System.out.println("Ruta="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
	}
	
	public class ListadoObjetivos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			ArrayList<ObjetivosBSC> listObjetivosBSC = ObjetivosBSC.getObjetivosByResult(result);		
			loadData(listObjetivosBSC);
		}
	}
	
	
	public class ListadoPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {		
			listaPeriodos = Periodo.getPeriodosByResult(result);
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
					listarObjetivos();
						 
					//EMF-//actualizaTabs();
				}
				
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
				}
			});
		}
	}
	

	
}
