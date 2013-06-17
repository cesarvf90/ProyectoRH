package pe.edu.pucp.proyectorh.evaluacion360;

import java.util.ArrayList;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.*;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;


public class Evaluar extends Fragment {
	View rootView;
	Context contexto;
	
	ExpandableListView listaProcesos;
	
	private ArrayList<ProcesoEvaluacion360> groups;
	private ArrayList<ArrayList<Evaluados360>> childs;
	
	Evaluacion360ExpandableAdapater adapter;
	private int numPagina;
	private int totalPaginas;
	int modoPrueba=1;
	
	public Evaluar(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private void activarBotonSiguiente() {
		Button botonSiguiente = (Button) rootView
				.findViewById(R.id.siguienteEvaluacionEva);
		botonSiguiente.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (numPagina < totalPaginas - 1) {
					//guardarRespuestas();
					numPagina++;
					//refreshLayout();
				} else {
					Toast.makeText(getActivity(),
							"Estas son las últimas preguntas de la evaluación",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void activarBotonAtras() {
		Button botonAtras = (Button) rootView
				.findViewById(R.id.atrasEvaluacionEva);
		botonAtras.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (numPagina > 0) {
					//guardarRespuestas();
					numPagina--;
					//refreshLayout();
				} else {
					Toast.makeText(
							getActivity(),
							"Estas son las primeras preguntas de la evaluación",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
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
		
		TextView p1 = (TextView)rootView.findViewById(R.id.pregunta1Eva);
		p1.setText("¿Cuenta con la capacidad 1?");
		TextView p2 = (TextView)rootView.findViewById(R.id.pregunta2Eva);
		p2.setText("¿Cuenta con la capacidad 2?");
		TextView p3 = (TextView)rootView.findViewById(R.id.pregunta3Eva);
		p3.setText("¿Cuenta con la capacidad 3?");
		TextView p4 = (TextView)rootView.findViewById(R.id.pregunta4Eva);
		p4.setText("¿Cuenta con la capacidad 4?");
		
		
		//TableLayout lay = (TableLayout)rootView.findViewById(R.id.layEvaluacion);
		//TableLayout lay2 = new TableLayout(contexto);
    	//for (int i=0;i<10;i++){
    		//String texto = "capacidad "+i;
    		//TextView txt = new TextView(contexto);
    		//txt.setText(texto);
    		
    		//RatingBar estrellitas = new RatingBar(contexto);
    		
    		//lay2.addView(txt);
    		//lay2.addView(estrellitas);
    	//}
    	//lay.addView(lay2);

		return rootView;
	}

}
