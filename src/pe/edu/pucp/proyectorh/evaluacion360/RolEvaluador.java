package pe.edu.pucp.proyectorh.evaluacion360;

import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.*;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosExpandableAdapter;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos.ListadoObjetivosChild;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos.ListadoPeriodos;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TabHost.OnTabChangeListener;


public class RolEvaluador extends Fragment {
	View rootView;
	Context contexto;
	
	ExpandableListView listaProcesos;
	
	private ArrayList<ProcesoEvaluacion360> groups;
	private ArrayList<ArrayList<Evaluados360>> childs;
	
	Evaluacion360ExpandableAdapater adapter;
	
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
	
	public void loadDataChild(ArrayList<Evaluados360> misEvaluados){
		
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
			Servicio.llamadaServicio(this.getActivity(), le,rutaLlamada); //SE LLAMA A VER MIS OBJETIVOS DEFINIDOS PARA MI
		
	    	System.out.println("new adapter");
	    	adapter = new Evaluacion360ExpandableAdapater(contexto, groups, childs);
	    	listaProcesos.setAdapter(adapter);
	    }
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView  = inflater.inflate(R.layout.rol_evaluador,container, false);
		contexto = rootView.getContext();
		rootView.findViewById(R.layout.rol_evaluador);
		
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
		Servicio.llamadaServicio(this.getActivity(), lp,rutaLlamada);

		return rootView;
	}

}
