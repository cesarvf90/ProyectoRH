package pe.edu.pucp.proyectorh.miinformacion;

import pe.edu.pucp.proyectorh.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EventoFragment extends Fragment {

	private View rootView;

	public EventoFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.weekly_calendar, container, false);
		return rootView;
	}
	
}
