package pe.edu.pucp.proyectorh.miinformacion;

import pe.edu.pucp.proyectorh.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AgendaFragment extends Fragment {

	private View rootView;

	public AgendaFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.mi_info_agenda,
				container, false);
		llamarServicioEventos();
		return rootView;
	}

	private void llamarServicioEventos() {
		// TODO cvasquez: llamar servicio para consultar eventos del usuario
		
	}
	
}
