package pe.edu.pucp.proyectorh.miinformacion;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Evento;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SemanaFragment extends Fragment {

	private View rootView;
	private ArrayList<Evento> eventos;

	public SemanaFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.weekly_calendar, container, false);
		mostrarEventos();
		return rootView;
	}

	/**
	 * Ubica los eventos en su posicion correspondiente de la semana y los muestra
	 */
	private void mostrarEventos() {
		RelativeLayout diaLayout = (RelativeLayout) rootView.findViewById(R.id.martesLayout);
		
		Button eventoButton = new Button(rootView.getContext());
		
        LinearLayout.LayoutParams layoutParametros = new LinearLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParametros.topMargin = 40;
        layoutParametros.height = 60;
        eventoButton.setText("Evento nuevo");
        eventoButton.setBackgroundColor(Color.CYAN);
        eventoButton.setLayoutParams(layoutParametros);
		
		diaLayout.addView(eventoButton);
	}
	
	
}
