package pe.edu.pucp.proyectorh.miinformacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

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
import android.app.AlertDialog;
import android.content.Intent;
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
		handler.post(calendarUpdater);

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
				SemanaFragment fragment = new SemanaFragment(eventos);
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.opcion_detail_container, fragment)
						.addToBackStack("tag").commit();
			}
		});

		gridview.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long id) {
				SemanaFragment fragment = new SemanaFragment(eventos);
				getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.opcion_detail_container, fragment)
						.addToBackStack("tag").commit();
				return false;
			}
		});

		return rootView;
	}

	public void refreshCalendar() {
		TextView title = (TextView) rootView.findViewById(R.id.title);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some random calendar items

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	public void onNewIntent(Intent intent) {
		intent.putExtra("date", "2013-5-2");
		String date = intent.getStringExtra("date");
		String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
		month.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
				Integer.parseInt(dateArr[2]));
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();
			// format random values. You can implement a dedicated class to
			// provide real values
			for (int i = 0; i < 31; i++) {

				// TODO no generar aleatorio sino que generar la misma
				// estructura a partir de los eventos recibidos en el servicio

				Random r = new Random();

				if (r.nextInt(10) > 6) {
					items.add(Integer.toString(i));
				}
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged(); // refresca la vista
		}
	};

	private void llamarServicioEventos() {
		// TODO cvasquez: llamar servicio para consultar eventos del usuario
		if (ConnectionManager.connect(getActivity())) {
			String fechaDesde = "28/05/2013%2000:00:00";
			String fechaHasta = "30/07/2013%2023:59:59";
			String request = Servicio.ObtenerEventos + "?colaboradorID="
					+ LoginActivity.getUsuario().getID() + "&fechaDesde="
					+ fechaDesde + "&fechaHasta=" + fechaHasta;
			new ObtencionEventos().execute(request);
		} else {
			ErrorServicio.mostrarErrorConexion(getActivity());
		}
	}

	public class ObtencionEventos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			try {
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
						evento.setCreador(eventoObject.getString("Creador"));
						JSONArray invitadosListObject = eventoObject
								.getJSONArray("Invitados");

						ArrayList<Colaborador> listaInvitados = new ArrayList<Colaborador>();
						for (int j = 0; j < invitadosListObject.length(); ++j) {
							JSONObject invitadoObject = invitadosListObject
									.getJSONObject(j);
							Colaborador invitado = new Colaborador();
							listaInvitados.add(invitado);
						}
						evento.setInvitados(listaInvitados);
						eventos.add(evento);
					}
					agregarEventosMock();
				}
			} catch (JSONException e) {
				ErrorServicio.mostrarErrorComunicacion(e.toString(),
						getActivity());
			} catch (NullPointerException ex) {
				ErrorServicio.mostrarErrorComunicacion(ex.toString(),
						getActivity());
			}
		}
	}

	private void agregarEventosMock() {
		Evento evento1 = new Evento();
		evento1.setNombre("Evento 1");
		evento1.setFechaInicio("05/06/2013");
		evento1.setFechaFin("05/06/2013");

		Evento evento2 = new Evento();
		evento2.setNombre("Evento 2");
		evento2.setFechaInicio("04/06/2013");
		evento2.setFechaFin("04/06/2013");

		Evento evento3 = new Evento();
		evento3.setNombre("Evento 3");
		evento3.setFechaInicio("08/06/2013");
		evento3.setFechaFin("08/06/2013");

		Evento evento4 = new Evento();
		evento4.setNombre("Evento 4");
		evento4.setFechaInicio("10/06/2013");
		evento4.setFechaFin("10/06/2013");
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
