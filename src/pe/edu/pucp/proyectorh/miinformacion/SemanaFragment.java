package pe.edu.pucp.proyectorh.miinformacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

	private static int UNA_HORA = 40;
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
		int horas = 1;
		for (final Evento evento : eventos) {

			RelativeLayout diaLayout = obtieneDiaLayout(evento);

			LinearLayout.LayoutParams layoutParametros = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			layoutParametros.topMargin = 5 * UNA_HORA;
			layoutParametros.height = horas++ * UNA_HORA;

			View eventoView = new View(getActivity());
			eventoView.setLayoutParams(layoutParametros);
			eventoView.setBackgroundColor(Color.CYAN);

			Button eventoButton = new Button(rootView.getContext());

			eventoButton.setText(evento.getNombre());
			eventoButton.setTag(evento.getNombre());
			eventoButton.setBackgroundColor(Color.CYAN);
			eventoButton.setLayoutParams(layoutParametros);

			diaLayout.addView(eventoView);
			diaLayout.addView(eventoButton);

			Button eventoSeleccionadoButton = (Button) rootView
					.findViewWithTag(evento.getNombre());
			eventoSeleccionadoButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
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

	/**
	 * Obtiene el layout correspondiente al dia en que se da el evento
	 * 
	 * @param evento
	 *            evento
	 * @return layout del dia correspondiente
	 */
	private RelativeLayout obtieneDiaLayout(Evento evento) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		Date fecha = new Date();
		try {
			fecha = dateFormat.parse(evento.getFechaInicio());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
		int diaDeLaSemana = c.get(Calendar.DAY_OF_WEEK);
		RelativeLayout diaLayout = new RelativeLayout(getActivity());

		switch (diaDeLaSemana) {
		case Calendar.MONDAY:
			diaLayout = (RelativeLayout) rootView
					.findViewById(R.id.lunesLayout);
			break;
		case Calendar.TUESDAY:
			diaLayout = (RelativeLayout) rootView
					.findViewById(R.id.martesLayout);
			break;
		case Calendar.WEDNESDAY:
			diaLayout = (RelativeLayout) rootView
					.findViewById(R.id.miercolesLayout);
			break;
		case Calendar.THURSDAY:
			diaLayout = (RelativeLayout) rootView
					.findViewById(R.id.juevesLayout);
			break;
		case Calendar.FRIDAY:
			diaLayout = (RelativeLayout) rootView
					.findViewById(R.id.viernesLayout);
			break;
		case Calendar.SATURDAY:
			diaLayout = (RelativeLayout) rootView
					.findViewById(R.id.sabadoLayout);
			break;
		case Calendar.SUNDAY:
			diaLayout = (RelativeLayout) rootView
					.findViewById(R.id.domingoLayout);
			break;
		}
		return diaLayout;
	}

	private Evento creaEventoMock() {
		Evento evento = new Evento();
		evento.setFechaInicio("04/06/2013");
		evento.setFechaFin("05/06/2013");
		evento.setNombre("Desayuno de la empresa");
		evento.setTipoEvento("Evento de la empresa");
		ArrayList<Colaborador> invitados = new ArrayList<Colaborador>();
		invitados.add(new Colaborador("César", "Vásquez Flores", "Tecnología",
				"Gerente"));
		invitados.add(new Colaborador("Miguel", "Dávila Reyes", "Tecnología",
				"Gerente"));
		invitados.add(new Colaborador("Jessica", "Ramirez Amarillo",
				"Tecnología", "Gerente"));
		evento.setInvitados(invitados);
		return evento;
	}
}
