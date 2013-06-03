package pe.edu.pucp.proyectorh.miinformacion;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Evento;
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
		for (Evento evento : eventos) {
			RelativeLayout diaLayout = (RelativeLayout) rootView
					.findViewById(R.id.martesLayout);

			Button eventoButton = new Button(rootView.getContext());

			LinearLayout.LayoutParams layoutParametros = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			layoutParametros.topMargin = 40;
			layoutParametros.height = 60;
			eventoButton.setText(evento.getNombre());
			// eventoButton.setTag("evento");
			eventoButton.setTag(evento.getNombre());
			eventoButton.setBackgroundColor(Color.CYAN);
			eventoButton.setLayoutParams(layoutParametros);

			diaLayout.addView(eventoButton);

			Button eventoSeleccionadoButton = (Button) rootView
					.findViewWithTag(evento.getNombre());
			eventoSeleccionadoButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Evento evento = creaEventoMock();
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

	private Evento creaEventoMock() {
		Evento evento = new Evento();
		evento.setCreador("C�sar V�squez Flores");
		evento.setFechaInicio("04/06/2013");
		evento.setFechaFin("05/06/2013");
		evento.setNombre("Desayuno de la empresa");
		evento.setTipo("Evento de la empresa");
		ArrayList<Colaborador> invitados = new ArrayList<Colaborador>();
		invitados.add(new Colaborador("C�sar", "V�squez Flores", "Tecnolog�a",
				"Gerente"));
		invitados.add(new Colaborador("Miguel", "D�vila Reyes", "Tecnolog�a",
				"Gerente"));
		invitados.add(new Colaborador("Jessica", "Ramirez Amarillo",
				"Tecnolog�a", "Gerente"));
		evento.setInvitados(invitados);
		return evento;
	}
}
