package pe.edu.pucp.proyectorh.evaluacion360;

import java.util.ArrayList;
import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.*;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.*;
import android.widget.*;


public class RolEvaluador extends Fragment {
	View rootView;
	Context contexto;
	FragmentActivity actv;
	
	ExpandableListView listaProcesos;
	
	private ArrayList<ProcesoEvaluacion360> groups;
	private ArrayList<ArrayList<Evaluados360>> childs;
	
	Evaluacion360ExpandableAdapater adapter;
	
	int modoPrueba=0;
	
	public RolEvaluador(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public class ListadoProcesos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			ArrayList<ProcesoEvaluacion360> listProcesos = ProcesoEvaluacion360.getProcesosByResult(result);		
			loadData(listProcesos);
		}
	}
	
	public class ListadoEvaluados extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			ArrayList<Evaluados360> listEvaluados = Evaluados360.getEvaluadosByResult(result);		
			loadDataChild(listEvaluados);
		}
	}
	
	public ArrayList<Evaluados360> obtenerHijos(int idProceso, ArrayList<Evaluados360> listEvals){
		ArrayList<Evaluados360> hijos = new ArrayList<Evaluados360>();
		System.out.println("obtiene hijos para padre="+idProceso);
    	for(int i=0;i<listEvals.size();i++){
    		System.out.println("esta en eval="+i + "con idProc="+listEvals.get(i).idProceso);
    		if(listEvals.get(i).idProceso==idProceso){
    			System.out.println("--->si cumple :)");
    			hijos.add(listEvals.get(i));
    		}
    	}
    	System.out.println("retornara con hijos cant="+hijos.size());
    	return hijos;
	}
	
	public void loadDataChild(ArrayList<Evaluados360> misEvaluados){
		for(int i=0;i<groups.size();i++){
			ArrayList<Evaluados360> hijitos = new ArrayList<Evaluados360>();
			hijitos = obtenerHijos(groups.get(i).idProceso,misEvaluados);
    		childs.add(hijitos);
    		System.out.println("se agrego data a childs para group="+groups.get(i).Nombre);
    	}
		System.out.println("finaliza con childscant="+childs.size());
    	adapter.actualizaHijos(childs);
	}
	
	public void llamadaFalsaProcesos(){
		System.out.println("data falsa de procesos");
		ArrayList<ProcesoEvaluacion360> listProcesos = new ArrayList<ProcesoEvaluacion360>();
		for(int i=0;i<2;i++){
			ProcesoEvaluacion360 eval = new ProcesoEvaluacion360();
			eval.Nombre = "procesos prueba "+i;
			eval.fecha = "04/06/2013";
			if (i  == 0){
				eval.estado = "Finalizado";
				eval.idProceso = 1;
			}else{
				eval.estado = "Pendiente";
				eval.idProceso = 2;
			}
			listProcesos.add(eval);
		}
		loadData(listProcesos);
	}

	public void llamadaFalsaEvaluados(){
		ArrayList<Evaluados360> evaluados = new ArrayList<Evaluados360>();
		for(int i=0;i<5;i++){
			Evaluados360 eval = new Evaluados360();
			eval.Nombre = "procesos prueba "+i;
			if (i % 2 == 0){
				eval.estado = "Evaluar";
				eval.idProceso = 1;
			}else{
				eval.estado = "Evaluado";
				eval.idProceso = 2;
			}
			evaluados.add(eval);
		}
		loadDataChild(evaluados);
	}
	
	private void loadData(ArrayList<ProcesoEvaluacion360> listProcesos){
	    	for(int i=0;i<listProcesos.size();i++){
	    		System.out.println("agrega proceso="+listProcesos.get(i).Nombre);
	    		groups.add(listProcesos.get(i));
	      	}
	    	
	    	ListadoEvaluados le = new ListadoEvaluados();
	    	String rutaLlamada ="";
	    	rutaLlamada = Servicio.ListarMisEvaluados360+"?idUsuario="+LoginActivity.getUsuario().getID(); 
		    System.out.println("Ruta-Hijos="+rutaLlamada);
		    if (modoPrueba==1){
		    	adapter = new Evaluacion360ExpandableAdapater(contexto,actv, groups, childs);
		    	listaProcesos.setAdapter(adapter);
		    	llamadaFalsaEvaluados();
		    }else{
		    	Servicio.llamadaServicio(this.getActivity(), le,rutaLlamada); //SE LLAMA A VER MIS OBJETIVOS DEFINIDOS PARA MI
		    	adapter = new Evaluacion360ExpandableAdapater(contexto,actv,groups, childs);
		    	listaProcesos.setAdapter(adapter);
		    }
	    	
	    }
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView  = inflater.inflate(R.layout.rol_evaluador,container, false);
		contexto = rootView.getContext();
		rootView.findViewById(R.layout.rol_evaluador);
		actv = getActivity();
		
		Resources res = getResources();
		
		/*
		 * CODIGO PARA MANEJO DE PERIODO (SPINNER)
		 */
		listaProcesos = (ExpandableListView) rootView.findViewById(R.id.listaProcesos);
    	groups= new ArrayList<ProcesoEvaluacion360>();
    	childs= new ArrayList<ArrayList<Evaluados360>>();
		listaProcesos.setLongClickable(true);
		ListadoProcesos lp = new ListadoProcesos();
		String rutaLlamada = Servicio.ListarProcesosEvaluacion360+"?idUsuario="+LoginActivity.getUsuario().getID();
		if(modoPrueba==1){
			llamadaFalsaProcesos();
		}else{
			Servicio.llamadaServicio(this.getActivity(), lp,rutaLlamada);
		}

		return rootView;
	}

}
