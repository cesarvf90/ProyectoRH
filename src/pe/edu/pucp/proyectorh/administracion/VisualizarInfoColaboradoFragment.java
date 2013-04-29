package pe.edu.pucp.proyectorh.administracion;

import pe.edu.pucp.proyectorh.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VisualizarInfoColaboradoFragment extends Fragment {

	public VisualizarInfoColaboradoFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.visualizar_info_colaborador,
				container, false);
		// ((TextView) rootView.findViewById(R.id.opcion_detail))
		// .setText("Fragmento de Rendir evaluaciones");
		return rootView;
	}

}
