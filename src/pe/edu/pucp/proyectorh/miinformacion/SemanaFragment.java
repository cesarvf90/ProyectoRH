package pe.edu.pucp.proyectorh.miinformacion;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Evento;
import pe.edu.pucp.proyectorh.reclutamiento.ConfirmacionEvaluacion;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SemanaFragment extends Fragment {

	private View rootView;
	private ArrayList<Evento> eventos;

	public SemanaFragment(ArrayList<Evento> eventos) {
		this.eventos = eventos;
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
	 * Ubica los eventos en su posicion correspondiente de la semana y los
	 * muestra
	 */
	private void mostrarEventos() {
		RelativeLayout diaLayout = (RelativeLayout) rootView
				.findViewById(R.id.martesLayout);

		Button eventoButton = new Button(rootView.getContext());

		LinearLayout.LayoutParams layoutParametros = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParametros.topMargin = 40;
		layoutParametros.height = 60;
		eventoButton.setText("Evento nuevo");
		eventoButton.setTag("evento");
		eventoButton.setBackgroundColor(Color.CYAN);
		eventoButton.setLayoutParams(layoutParametros);

		diaLayout.addView(eventoButton);

		Button eventoSeleccionadoButton = (Button) rootView
				.findViewWithTag("evento");
		eventoSeleccionadoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Evento evento = new Evento();
				evento.setCreador(new Colaborador("César", "Vásquez Flores",
						"Tecnología", "Gerente"));
				evento.setFechaInicio("04/06/2013");
				evento.setFechaFin("05/06/2013");
				evento.setNombre("Desayuno de la empresa");
				evento.setTipo("Evento de la empresa");
				ArrayList<Colaborador> invitados = new ArrayList<Colaborador>();
				invitados.add(new Colaborador("César", "Vásquez Flores",
						"Tecnología", "Gerente"));
				invitados.add(new Colaborador("Miguel", "Dávila Reyes",
						"Tecnología", "Gerente"));
				invitados.add(new Colaborador("Jessica", "Ramirez Amarillo",
						"Tecnología", "Gerente"));
				evento.setInvitados(invitados);
				FragmentTransaction ft = getActivity()
						.getSupportFragmentManager().beginTransaction();
				EventoFragment fragment = new EventoFragment(evento);
				ft.setCustomAnimations(android.R.anim.slide_in_left,
						android.R.anim.slide_out_right);
				ft.replace(R.id.opcion_detail_container, fragment,
						"detailFragment").addToBackStack("tag").commit();
			}
		});
	}
}
