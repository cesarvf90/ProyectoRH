package pe.edu.pucp.proyectorh.miinformacion;

import pe.edu.pucp.proyectorh.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactosFragment extends Fragment {

	public ContactosFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.mi_info_contactos, container,
				false);
		return rootView;
	}

}
