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


public class Evaluar extends Fragment {
	View rootView;
	Context contexto;
	
	ExpandableListView listaProcesos;
	
	private ArrayList<ProcesoEvaluacion360> groups;
	private ArrayList<ArrayList<Evaluados360>> childs;
	
	Evaluacion360ExpandableAdapater adapter;
	
	int modoPrueba=1;
	
	public Evaluar(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView  = inflater.inflate(R.layout.evaluar,container, false);
		contexto = rootView.getContext();
		rootView.findViewById(R.layout.evaluar);
		
		Resources res = getResources();
		
		/*
		 * CODIGO PARA MANEJO DE PERIODO (SPINNER)
		 */
		QuickContactBadge imagen = (QuickContactBadge) rootView.findViewById(R.id.evaluacionImagenContacto);
		TableLayout lay = (TableLayout)rootView.findViewById(R.id.layEvaluacion);
		TableLayout lay2 = new TableLayout(contexto);
    	for (int i=0;i<10;i++){
    		String texto = "capacidad "+i;
    		TextView txt = new TextView(contexto);
    		txt.setText(texto);
    		
    		RatingBar estrellitas = new RatingBar(contexto);
    		
    		lay2.addView(txt);
    		lay2.addView(estrellitas);
    	}
    	lay.addView(lay2);

		return rootView;
	}

}
