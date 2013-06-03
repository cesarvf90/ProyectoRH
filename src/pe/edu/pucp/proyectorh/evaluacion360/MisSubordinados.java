package pe.edu.pucp.proyectorh.evaluacion360;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.AvanceDeObjetivo;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Evaluador;
import pe.edu.pucp.proyectorh.model.Objetivo;
import pe.edu.pucp.proyectorh.model.Proceso;
import pe.edu.pucp.proyectorh.utils.AdaptadorDeObjetivos;
import pe.edu.pucp.proyectorh.utils.EvaluacionesAdaptador;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

public class MisSubordinados extends Fragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View elLadoDerecho = inflater.inflate(R.layout.subordinados_360, container, false);
		Context contexto = elLadoDerecho.getContext();
		
//		elLadoDerecho.findViewById(R.layout.subordinados_360);
		
		Spinner elProcesoDeLaLista = (Spinner) elLadoDerecho.findViewById(R.id.elNombre);
		
//		ArrayAdapter losDatos = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, Proceso.tomarDatosTemporales());
		ArrayAdapter losDatos = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, Proceso.entregarListado());
		losDatos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		elProcesoDeLaLista.setAdapter(losDatos);
		
		preparaLaSupervisionDePruebas(elLadoDerecho);
		
		return elLadoDerecho;
	}		
	
	private void preparaLaSupervisionDePruebas(View laPantalla)
	{
		ExpandableListView losEvaluados = (ExpandableListView) laPantalla.findViewById(R.id.laSupervisionDeEvaluaciones);
		EvaluacionesAdaptador lasEvaluaciones = new EvaluacionesAdaptador(this.getActivity().getApplicationContext(), Colaborador.devolverSubordinadosFicticios(), Evaluador.agruparEvaluadores());
		losEvaluados.setAdapter(lasEvaluaciones);
		losEvaluados.setLongClickable(true);
				
		
		
	}
}
