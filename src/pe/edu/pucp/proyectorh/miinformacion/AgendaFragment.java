package pe.edu.pucp.proyectorh.miinformacion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Evento;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.ErrorServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.CalendarAdapter;
import pe.edu.pucp.proyectorh.utils.EstiloApp;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class AgendaFragment extends Fragment {

	public Calendar month;
	public CalendarAdapter adapter;
	public Handler handler;
	public ArrayList<String> items;
	private ArrayList<Evento> eventos;
	private View rootView;
	private Date fechaActual;

	public AgendaFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.calendar, container, false);

		llamarServicioEventos();

		month = Calendar.getInstance();
		onNewIntent(getActivity().getIntent());

		items = new ArrayList<String>();
		adapter = new CalendarAdapter(this.getActivity(), month);

		GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
		gridview.setAdapter(adapter);

		handler = new Handler();

		TextView title = (TextView) rootView.findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		// Mes anterior
		TextView previous = (TextView) rootView.findViewById(R.id.previous);
		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month.get(Calendar.MONTH) == month
						.getActualMinimum(Calendar.MONTH)) {
					month.set((month.get(Calendar.YEAR) - 1),
							month.getActualMaximum(Calendar.MONTH), 1);
				} else {
					month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
				}
				refreshCalendar();
			}
		});

		// Mes siguiente
		TextView next = (TextView) rootView.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month.get(Calendar.MONTH) == month
						.getActualMaximum(Calendar.MONTH)) {
					month.set((month.get(Calendar.YEAR) + 1),
							month.getActualMinimum(Calendar.MONTH), 1);
				} else {
					month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
				}
				refreshCalendar();
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (adapter.days[position] != "") {
					int diaSelecccionado = Integer
							.valueOf(adapter.days[position]);
					Calendar diaEscogido = new GregorianCalendar();
					diaEscogido.set(month.get(Calendar.YEAR),
							month.get(Calendar.MONTH) - 1, diaSelecccionado);
					int primerDiaSemana = diaEscogido.getFirstDayOfWeek();
					System.out.println(diaEscogido.toString());
					SemanaFragment fragment = new SemanaFragment(eventos,
							month, primerDiaSemana);
					getActivity().getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.addToBackStack("tag").commit();
				}
			}
		});

		gridview.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long id) {
				if (adapter.days[position] != "") {
					int diaSelecccionado = Integer
							.valueOf(adapter.days[position]);
					Calendar diaEscogido = new GregorianCalendar();
					diaEscogido.set(month.get(Calendar.YEAR),
							month.get(Calendar.MONTH) - 1, diaSelecccionado);
					int primerDiaSemana = diaEscogido.getFirstDayOfWeek();
					System.out.println(diaEscogido.toString());
					SemanaFragment fragment = new SemanaFragment(eventos,
							month, primerDiaSemana);
					getActivity().getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.addToBackStack("tag").commit();
				}
				return false;
			}
		});

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

	public void refreshCalendar() {
		TextView title = (TextView) rootView.findViewById(R.id.title);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	public void onNewIntent(Intent intent) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		fechaActual = new Date();
		String[] dateArr = dateFormat.format(fechaActual).split("/");
		month.set(Integer.parseInt(dateArr[0]),
				Integer.parseInt(dateArr[1]) - 1,
				Integer.parseInt(dateArr[2]) - 1);
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			int mes = month.get(Calendar.MONTH);
			int anio = month.get(Calendar.YEAR);
			items.clear();
			// genera una estructura a partir de los eventos recibidos del
			// servicio y que se deben mostrar en ese mes
			for (Evento evento : eventos) {
				// se valida las fechas que estan en el mes actual
				String[] dateArr = evento.getFechaInicio().split("/");
				if (mes + 1 == Integer.valueOf(dateArr[1])
						&& anio == Integer.valueOf(dateArr[2].substring(0, 4))) {
					items.add(dateArr[0]);
				}
			}
			adapter.setItems(items);
			adapter.notifyDataSetChanged(); // refresca la vista
		}
	};

	private void llamarServicioEventos() {
		if (ConnectionManager.connect(getActivity())) {
			// TODO cvasquez obtener fecha inicial de un mes atras y final de un
			// mes adelante
			// String fechaDesde = fechaActual;
			String fechaDesde = "28/05/2013%2000:00:00";
			String fechaHasta = "30/07/2013%2023:59:59";
			String request = Servicio.ObtenerEventos + "?colaboradorID="
					+ LoginActivity.getUsuario().getID() + "&fechaDesde="
					+ fechaDesde + "&fechaHasta=" + fechaHasta;
			new ObtencionEventos(this.getActivity()).execute(request);
		} else {
			ErrorServicio.mostrarErrorConexion(getActivity());
		}
	}

	public class ObtencionEventos extends AsyncCall {

		public ObtencionEventos(Activity activity) {
			super(activity);
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			try {
				ArrayList<Colaborador> invitadosMock = generarInvitadosMock();
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (procesaRespuesta(respuesta)) {
					JSONObject datosObject = (JSONObject) jsonObject
							.get("data");
					JSONArray eventosListObject = (JSONArray) datosObject
							.get("eventos");
					eventos = new ArrayList<Evento>();
					for (int i = 0; i < eventosListObject.length(); ++i) {
						JSONObject eventoObject = eventosListObject
								.getJSONObject(i);
						Evento evento = new Evento();
						evento.setID(eventoObject.getInt("ID"));
						evento.setNombre(eventoObject.getString("Nombre"));
						evento.setFechaInicio(eventoObject.getString("Inicio"));
						evento.setFechaFin(eventoObject.getString("Fin"));
						evento.setEstadoID(eventoObject.getInt("EstadoID"));
						evento.setEstado(eventoObject.getString("Estado"));
						evento.setCreadorID(eventoObject.getInt("CreadorID"));
						// evento.setCreador(eventoObject.getString("Creador"));
						evento.setCreador(new Colaborador("César",
								"Vásquez Flores", "Tecnología", "Gerente"));
						evento.setTipoEventoID(eventoObject
								.getInt("TipoEventoID"));
						evento.setTipoEvento(eventoObject
								.getString("TipoEvento"));
						// JSONArray invitadosListObject = eventoObject
						// .getJSONArray("Invitados");
						//
						// listaInvitados = new ArrayList<Colaborador>();
						// for (int j = 0; j < invitadosListObject.length();
						// ++j) {
						// JSONObject invitadoObject = invitadosListObject
						// .getJSONObject(j);
						// Colaborador invitado = new Colaborador();
						// invitado.setNombreCompleto(invitadoObject
						// .getString("NombreCompleto"));
						// invitado.setNombres(invitadoObject
						// .getString("Nombre"));
						// invitado.setApellidos(invitadoObject
						// .getString("ApellidoPaterno")
						// + Constante.ESPACIO_VACIO
						// + invitadoObject
						// .getString("ApellidoMaterno"));
						// invitado.setArea(invitadoObject.getString("Area"));
						// invitado.setPuesto(invitadoObject
						// .getString("Puesto"));
						// invitado.setTelefono(invitadoObject
						// .getString("Telefono"));
						// invitado.setCorreoElectronico(invitadoObject
						// .getString("CorreoElectronico"));
						// listaInvitados.add(invitado);
						// }
						// evento.setInvitados(listaInvitados);
						evento.setInvitados(invitadosMock);
						eventos.add(evento);
					}
					// agregarEventosMock();
					handler.post(calendarUpdater);
					ocultarMensajeProgreso();
				}
			} catch (JSONException e) {
				ocultarMensajeProgreso();
				ErrorServicio.mostrarErrorComunicacion(e.toString(),
						getActivity());
			} catch (NullPointerException ex) {
				ocultarMensajeProgreso();
				ErrorServicio.mostrarErrorComunicacion(ex.toString(),
						getActivity());
			}
		}
	}

	private ArrayList<Colaborador> generarInvitadosMock() {
		ArrayList<Colaborador> invitados = new ArrayList<Colaborador>();
		invitados.add(new Colaborador("César", "Vásquez Flores", "Tecnología",
				"Gerente"));
		invitados.add(new Colaborador("Karina", "Sotil Retamoso",
				"Contabilidad", "Analista"));
		invitados.add(new Colaborador("Claudio", "Pizarro Guerrero", "RRHH",
				"Evaluador"));
		invitados.add(new Colaborador("Jessica", "Rojas Romero", "Operaciones",
				"Gerente"));
		invitados.add(new Colaborador("Joel", "Flores Copa", "Operaciones",
				"Analista"));
		return invitados;
	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (ConstanteServicio.SERVICIO_OK.equals(respuestaServidor)) {
			return true;
		} else if (ConstanteServicio.SERVICIO_ERROR.equals(respuestaServidor)) {
			// Se muestra mensaje de servicio no disponible
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Servicio no disponible");
			builder.setMessage("No se pudieron obtener los eventos de su agenda. Intente nuevamente");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return false;
		} else {
			// Se muestra mensaje de la respuesta invalida del servidor
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Problema en el servidor");
			builder.setMessage("Hay un problema en el servidor.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return false;
		}
	}

}
