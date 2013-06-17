package pe.edu.pucp.proyectorh.miinformacion;

import java.util.Collections;
import java.util.Comparator;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Evento;
import pe.edu.pucp.proyectorh.utils.Constante;
import pe.edu.pucp.proyectorh.utils.EstiloApp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Funcionalidad para observar el detalle de un evento y sus invitados
 * 
 * @author cvasquez
 * 
 */
@SuppressLint({ "ValidFragment", "ValidFragment", "ValidFragment" })
public class EventoFragment extends Fragment {

	private View rootView;
	private Evento evento;

	public EventoFragment(Evento evento) {
		this.evento = evento;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.evento_layout, container, false);
		mostrarEvento();
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

	private void mostrarEvento() {
		// Datos del evento
		TextView nombreEventoText = (TextView) rootView
				.findViewById(R.id.nombre_evento);
		mostrarTexto(nombreEventoText, evento.getNombre());
		TextView tipoEventoText = (TextView) rootView
				.findViewById(R.id.tipo_evento_content);
		mostrarTexto(tipoEventoText, evento.getTipoEvento());
		TextView fechaInicioText = (TextView) rootView
				.findViewById(R.id.fecha_inicio_content);
		mostrarTexto(fechaInicioText, evento.getFechaInicio());
		TextView fechaFinText = (TextView) rootView
				.findViewById(R.id.fecha_fin_content);
		mostrarTexto(fechaFinText, evento.getFechaFin());
		TextView lugarEventoText = (TextView) rootView
				.findViewById(R.id.lugar_evento_content);
		mostrarTexto(lugarEventoText, evento.getLugar());

		// Datos del creador
		TextView nombreCreadorText = (TextView) rootView
				.findViewById(R.id.nombre_creador_content);
		nombreCreadorText.setText(evento.getCreador().getNombreCompleto());
		TextView areaCreadorText = (TextView) rootView
				.findViewById(R.id.area_creador_content);
		areaCreadorText.setText(evento.getCreador().getArea());
		TextView puestoCreadorText = (TextView) rootView
				.findViewById(R.id.puesto_creador_content);
		puestoCreadorText.setText(evento.getCreador().getPuesto());

		TextView creadorTituloText = (TextView) rootView
				.findViewById(R.id.title_creador);
		TextView invitadosTituloText = (TextView) rootView
				.findViewById(R.id.title_invitados);
		pintarSegunTipoEvento(nombreEventoText, creadorTituloText,
				invitadosTituloText);

		ListView listaInvitados = (ListView) rootView
				.findViewById(R.id.lista_invitados_evento);
		Collections.sort(evento.getInvitados(), new Comparator<Colaborador>() {

			@Override
			public int compare(Colaborador colaborador1,
					Colaborador colaborador2) {
				return colaborador1.toString().compareTo(
						colaborador2.toString());

			}
		});
		final ArrayAdapter<Colaborador> invitadosAdapter = new ArrayAdapter<Colaborador>(
				this.getActivity(), android.R.layout.simple_list_item_1,
				evento.getInvitados()) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView view = (TextView) super.getView(position, convertView,
						parent);
				view.setTypeface(Typeface.createFromAsset(getActivity()
						.getAssets(), EstiloApp.FORMATO_LETRA_APP));
				return view;
			}
		};
		listaInvitados.setAdapter(invitadosAdapter);
		listaInvitados
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent,
							View childView, int position, long id) {
						// position tiene la posicion de la vista en el adapter
						mostrarInvitadoSeleccionado(evento.getInvitados().get(
								position));
						invitadosAdapter.notifyDataSetChanged();
					}
				});
	}

	private void pintarSegunTipoEvento(TextView nombreEventoText,
			TextView creadorTituloText, TextView invitadosTituloText) {
		int color = 0;
		if (Evento.EVENTO_EMPRESA.equals(evento.getTipoEvento())) {
			color = Color.parseColor("#0B6121");
		} else if (Evento.EVENTO_PERSONAL.equals(evento.getTipoEvento())) {
			color = Color.parseColor("#0B0B61");
		} else if (Evento.EVENTO_ESPECIAL.equals(evento.getTipoEvento())) {
			color = Color.parseColor("#5F04B4");
		}
		nombreEventoText.setBackgroundColor(color);
		creadorTituloText.setBackgroundColor(color);
		invitadosTituloText.setBackgroundColor(color);
	}

	protected void mostrarInvitadoSeleccionado(Colaborador invitado) {
		TextView nombreText = (TextView) rootView
				.findViewById(R.id.invitado_nombre_content);
		nombreText.setText(invitado.getNombres() + Constante.ESPACIO_VACIO
				+ invitado.getApellidos());
		TextView areaText = (TextView) rootView
				.findViewById(R.id.invitado_area_content);
		areaText.setText(invitado.getArea());
		TextView puestoText = (TextView) rootView
				.findViewById(R.id.invitado_puesto_content);
		puestoText.setText(invitado.getPuesto());
		TextView telefonoText = (TextView) rootView
				.findViewById(R.id.invitado_telefono_content);
		telefonoText.setText(invitado.getPuesto());
		TextView correoText = (TextView) rootView
				.findViewById(R.id.invitado_correo_content);
		correoText.setText(invitado.getPuesto());
	}

	private void mostrarTexto(TextView textView, String texto) {
		if (texto != null) {
			textView.setText(texto);
		} else {
			textView.setText(Constante.ESPACIO_VACIO);
		}
	}
}
