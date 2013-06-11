package pe.edu.pucp.proyectorh.evaluacion360;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.evaluacion360.RolEvaluador.ListadoProcesos;
import pe.edu.pucp.proyectorh.model.Evaluados360;
import pe.edu.pucp.proyectorh.model.ProcesoEvaluacion360;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.MisEvaluacionesAdaptador;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class RolEvaluado extends Fragment {

	
	
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
		
		View laPantalla = inflater.inflate(R.layout.rol_evaluado, container, false);
		
		ExpandableListView losResultados = (ExpandableListView) laPantalla.findViewById(R.id.examenes);
		
		MisEvaluacionesAdaptador interfaz = new MisEvaluacionesAdaptador(container.getContext());
		
		losResultados.setAdapter(interfaz);
		
		return laPantalla;
	}	
}
