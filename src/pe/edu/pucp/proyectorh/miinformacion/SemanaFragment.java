package pe.edu.pucp.proyectorh.miinformacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Evento;
import pe.edu.pucp.proyectorh.utils.EstiloApp;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SemanaFragment extends Fragment {

	private static int UNA_HORA = 40;
	private View rootView;
	private ArrayList<Evento> eventos;
	private Calendar month;
	private Calendar diaEscogido;
	private int primerDiaSemana;
	private int ultimoDiaSemana;
	private int diaDeLaSemana;

	public SemanaFragment(ArrayList<Evento> eventos, Calendar month,
			Calendar diaEscogido) {
		this.eventos = eventos;
		this.month = month;
		this.diaEscogido = diaEscogido;
		Date fechaEscogida = new Date();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			fechaEscogida = formatter.parse(String.valueOf(diaEscogido
					.get(Calendar.YEAR))
					+ "/"
					+ String.valueOf(diaEscogido.get(Calendar.MONTH) + 1)
					+ "/"
					+ String.valueOf(diaEscogido.get(Calendar.DAY_OF_MONTH)));
		} catch (ParseException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(fechaEscogida);
		// TODO cvasquez: validar bien el dia de la semana porque cambia el
		// calculo para cada fecha segun lo que estaba previsto
		// diaDeLaSemana = (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;
		// if (diaDeLaSemana == 0) {
		// diaDeLaSemana = 7;
		// }
		diaDeLaSemana = c.get(Calendar.DAY_OF_WEEK);
		System.out.println("Día número " + diaDeLaSemana + " en la semana");
		int diaFechaActual = diaEscogido.get(Calendar.DAY_OF_MONTH);
		primerDiaSemana = diaFechaActual - diaDeLaSemana + 1;
		ultimoDiaSemana = diaFechaActual + 7 - diaDeLaSemana;
		System.out.println("Semana del " + primerDiaSemana + " al "
				+ ultimoDiaSemana);
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
		customizarEstilos(getActivity(), rootView);
		return rootView;
	}

	private void customizarEstilos(Context context, View view) {
		try {
			if (view instanceof ViewGroup) {
				ViewGroup vg = (ViewGroup) view;
				for (int i = 0; i < vg.getChildCount(); i++) {
					View child = vg.getChildAt(i);
					customizarEstilos(context, child);
				}
			} else if (view instanceof TextView) {
				((TextView) view).setTypeface(Typeface.createFromAsset(
						context.getAssets(), EstiloApp.FORMATO_LETRA_APP));
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Ubica los eventos en su posicion correspondiente de la semana y los
	 * muestra
	 */
	private void mostrarEventos() {
		TextView semanaTitleText = (TextView) rootView
				.findViewById(R.id.semana_title);
		semanaTitleText.setText("Semana del "
				+ diaEscogido.get(Calendar.DAY_OF_MONTH) + " de "
				+ android.text.format.DateFormat.format("MMMM", month)
				+ " del " + month.get(Calendar.YEAR));
		for (final Evento evento : eventos) {
			if (esEventoDeLaSemana(evento)) {

				// Se evalua el dia para ubicarlo en un layout
				RelativeLayout diaLayout = obtieneDiaLayout(evento);

				// Se evaluan las horas para ubicarlo con margenes y longitud
				RelativeLayout.LayoutParams layoutParametros = new RelativeLayout.LayoutParams(
						LayoutParams.FILL_PARENT, obtenerHorasDuracion(evento)
								* UNA_HORA);
				layoutParametros.topMargin = obtenerHoraInicial(evento)
						* UNA_HORA;

				View eventoView = new View(getActivity());
				eventoView.setLayoutParams(layoutParametros);
				eventoView.setBackgroundColor(Color.CYAN);

				Button eventoButton = new Button(rootView.getContext());

				eventoButton.setText(evento.getNombre());
				eventoButton.setTextColor(Color.WHITE);
				eventoButton.setTag(evento.getID());
				pintarEvento(eventoView, evento);
				diaLayout.addView(eventoView);
				diaLayout.addView(eventoButton);
				eventoView.setLayoutParams(layoutParametros);
				eventoButton.setLayoutParams(layoutParametros);

				Button eventoSeleccionadoButton = (Button) rootView
						.findViewWithTag(evento.getID());
				eventoSeleccionadoButton
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								System.out.println("Se selecciono el evento");
								FragmentTransaction ft = getActivity()
										.getSupportFragmentManager()
										.beginTransaction();
								EventoFragment fragment = new EventoFragment(
										evento);
								ft.setCustomAnimations(
										android.R.anim.slide_in_left,
										android.R.anim.slide_out_right);
								ft.replace(R.id.opcion_detail_container,
										fragment, "detailFragment")
										.addToBackStack("tag").commit();
							}
						});
			}
		}
	}

	private boolean esEventoDeLaSemana(Evento evento) {
		// Day of week : 1 - miercoles
		// TODO cvasquez: validar eventos de un mes pasado o siguiente en la
		// semana consultada
		String fecha[] = evento.getFechaInicio().split("/");
		int diaEvento = Integer.parseInt(fecha[0]);
		int mesEvento = Integer.parseInt(fecha[1]);
		if (mesEvento != (month.get(Calendar.MONTH) + 1)) {
			return false;
		}
		if ((diaEvento >= primerDiaSemana) && (diaEvento <= ultimoDiaSemana)) {
			System.out
					.println("Evento del " + diaEvento + " está en la semana");
			return true;
		} else {
			System.out.println("Evento del " + diaEvento
					+ " no está en la semana");
			return false;
		}
	}

	private void pintarEvento(View eventoButton, Evento evento) {
		if (Evento.EVENTO_EMPRESA.equals(evento.getTipoEvento())) {
			eventoButton.setBackgroundColor(Color.parseColor("#008000"));
		} else if (Evento.EVENTO_PERSONAL.equals(evento.getTipoEvento())) {
			eventoButton.setBackgroundColor(Color.parseColor("#000080"));
		} else if (Evento.EVENTO_ESPECIAL.equals(evento.getTipoEvento())) {
			eventoButton.setBackgroundColor(Color.parseColor("#800080"));
		}
	}

	private int obtenerHoraInicial(Evento evento) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
		Date fechaInicio = new Date();
		try {
			fechaInicio = dateFormat.parse(evento.getFechaInicio());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaInicio.getHours();
	}

	private int obtenerHorasDuracion(Evento evento) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
		Date fechaInicio = new Date();
		Date fechaFin = new Date();
		try {
			fechaInicio = dateFormat.parse(evento.getFechaInicio());
			fechaFin = dateFormat.parse(evento.getFechaFin());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fechaFin.getHours() - fechaInicio.getHours();
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

}
