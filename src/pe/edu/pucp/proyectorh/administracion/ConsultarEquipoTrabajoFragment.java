package pe.edu.pucp.proyectorh.administracion;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.ColaboradorEquipoTrabajo;
import pe.edu.pucp.proyectorh.model.InfoColaborador;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.google.gson.Gson;

public class ConsultarEquipoTrabajoFragment extends Fragment {

	private static final String OPERACION_VALIDA = "1";
	private static final String OPERACION_INVALIDA = "0";
	private View rootView;
	private ExpandableListView exv;
	private ArrayList<String> groups;
	private ArrayList<ArrayList<ArrayList<String>>> childs;
	private ArrayList<ColaboradorEquipoTrabajo> padres;
	private ArrayList<ArrayList<ArrayList<ColaboradorEquipoTrabajo>>> hijos;
	private ColaboradorEquipoTrabajo colaborador;
	private ColaboradorEquipoTrabajo jefe;
	private ColaboradorEquipoTrabajo subordinadoNivel1;
	private ColaboradorEquipoTrabajo subordinadoNivel2;

	public ConsultarEquipoTrabajoFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.consultar_equipo_trabajo,
				container, false);
		exv = (ExpandableListView) this.rootView
				.findViewById(R.id.equipo_trabajo_contactos_list);

		loadData();
		// llamarServicioConsultarEquipoTrabajo(LoginActivity.idUsuario);
		probarDeserializacionGSON();
		/*
		 * MyExpandableAdapter adapter = new MyExpandableAdapter(this
		 * .getActivity().getApplicationContext(), groups, childs);
		 * exv.setAdapter(adapter);
		 */

		AdapterEquipoObjetosColaboradores adapter = new AdapterEquipoObjetosColaboradores(
				this.getActivity().getApplicationContext(), padres, hijos);
		exv.setAdapter(adapter);

		exv.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				System.out.println("Grupo " + (groupPosition));

				mostrarDatosPadre(groupPosition);
				return false;
			}
		});

		return rootView;
	}

	protected void mostrarDatosPadre(int groupPosition) {
		String e = groups.get(groupPosition);
		System.out.println(e);
		pintarLadoDerecho(padres.get(groupPosition));
	}

	private void llamarServicioConsultarEquipoTrabajo(String usuario) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.InformacionPersonalService + "?username="
					+ usuario;
			System.out.println("pagina: " + request);
			new deserializarJSON().execute(request);
		}
	}

	public class deserializarJSON extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			//MISMA LOGICA QUE probarDeserializacionGSON
			//String result = "{\"respuesta\":\"1\",\"jefeEquipo\":{\"nombreCompleto\":\"Juan Perez\",\"cargo\":\"Jefe de �rea\",\"area\":\"Logistica y Operaciones\",\"puesto\":\"Subgerente de An�lisis\",\"anexo\":\"2891\",\"email\":\"jperez@rhpp.com\",\"cantidadSubordinados\":\"3\",\"listaSubordinados\":{\"colaborador1\":{\"nombreCompleto\":\"Carla Sanchez\",\"cargo\":\"xxxxx\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"2\",\"listaSubordinados\":{\"subcolaborador1\":{\"nombreCompleto\":\"practicante1A\",\"cargo\":\"xxxxx\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}},\"subcolaborador2\":{\"nombreCompleto\":\"practicante2A\",\"cargo\":\"xxxxx\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}},\"colaborador2\":{\"nombreCompleto\":\"Mateo Soto\",\"cargo\":\"xxxxx\",	\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"1\",\"listaSubordinados\":{\"subcolaborador1\":{\"nombreCompleto\":\"practicante1B\",\"cargo\":\"xxxxx\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}},\"colaborador3\":{\"nombreCompleto\":\"Diego Bernal\",\"cargo\":\"xxxxx\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}}}";
			// System.out.println("Recibido: " + result.toString());
			// deserializando el json parte por parte
			try {
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("respuesta");
				if (procesaRespuesta(respuesta)) {
					System.out.println("procesa respuesta");
					// Obtenemos el jefe de todos --NIVEL 0
					JSONObject jefeObject = (JSONObject) jsonObject
							.get("jefeEquipo");
					jefe = new ColaboradorEquipoTrabajo(
							jefeObject.getString("nombreCompleto"),
							jefeObject.getString("cargo"),
							jefeObject.getString("area"),
							jefeObject.getString("puesto"),
							jefeObject.getString("anexo"),
							jefeObject.getString("email"),
							jefeObject.getInt("cantidadSubordinados"));
					System.out.println(jefe.toString());
					// Obtenemos la lista de subordinados del jefe de todos
					// --NIVEL
					// 1
					JSONObject listaSubordinadosNivel1 = (JSONObject) jefeObject
							.get("listaSubordinados");

					// Para cada uno de los subordinados del jefe de todos
					JSONObject subordinadoNivel1Object;
					padres = new ArrayList<ColaboradorEquipoTrabajo>();
					hijos = new ArrayList<ArrayList<ArrayList<ColaboradorEquipoTrabajo>>>();
					for (int i = 1; i <= jefe.getCantidadSubordinados(); i++) {
						subordinadoNivel1Object = (JSONObject) listaSubordinadosNivel1
								.get("colaborador" + String.valueOf(i));

						subordinadoNivel1 = new ColaboradorEquipoTrabajo(
								subordinadoNivel1Object
										.getString("nombreCompleto"),
								subordinadoNivel1Object.getString("cargo"),
								subordinadoNivel1Object.getString("area"),
								subordinadoNivel1Object.getString("puesto"),
								subordinadoNivel1Object.getString("anexo"),
								subordinadoNivel1Object.getString("email"),
								subordinadoNivel1Object
										.getInt("cantidadSubordinados"));

						padres.add(subordinadoNivel1);
						System.out.println(subordinadoNivel1.toString());
						JSONObject listaSubordinadosNivel2 = (JSONObject) subordinadoNivel1Object
								.get("listaSubordinados");

						JSONObject subordinadoNivel2Object;
						hijos.add(new ArrayList<ArrayList<ColaboradorEquipoTrabajo>>());
						// childs.add(new ArrayList<ArrayList<String>>());
						for (int j = 1; j <= subordinadoNivel1
								.getCantidadSubordinados(); j++) {
							subordinadoNivel2Object = (JSONObject) listaSubordinadosNivel2
									.get("subcolaborador" + String.valueOf(j));

							subordinadoNivel2 = new ColaboradorEquipoTrabajo(
									subordinadoNivel2Object
											.getString("nombreCompleto"),
									subordinadoNivel2Object.getString("cargo"),
									subordinadoNivel2Object.getString("area"),
									subordinadoNivel2Object.getString("puesto"),
									subordinadoNivel2Object.getString("anexo"),
									subordinadoNivel2Object.getString("email"),
									subordinadoNivel2Object
											.getInt("cantidadSubordinados"));

							hijos.get(i - 1).add(
									new ArrayList<ColaboradorEquipoTrabajo>());
							hijos.get(i - 1).get(j - 1).add(subordinadoNivel2);
							/*
							 * 
							 * childs.get(0).add(new ArrayList<String>());
							 * childs.get(0).get(0).add("practicante 1A");
							 * childs.get(0).add(new ArrayList<String>());
							 * childs.get(0).get(1).add("practicante 1B");
							 * 
							 * childs.add(new ArrayList<ArrayList<String>>());
							 * childs.get(1).add(new ArrayList<String>());
							 * childs.get(1).get(0).add("practicante 2B");
							 * 
							 * childs.add(new ArrayList<ArrayList<String>>());
							 */

							System.out.println(subordinadoNivel2.toString());
						}
					}
				}
			} catch (JSONException e) {
				System.out.println("entre al catch1");
				System.out.println(e.toString());
				mostrarErrorComunicacion(e.toString());
			} catch (NullPointerException ex) {
				System.out.println("entre al catch2");
				System.out.println(ex.toString());
				mostrarErrorComunicacion(ex.toString());
			}
		}
	}

	public void pintarLadoDerecho(ColaboradorEquipoTrabajo colaborador) {
		TextView nombreCompleto = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_nombreCompleto);
		nombreCompleto.setText(colaborador.getNombreCompleto());

		TextView cargo = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_cargo_value);
		cargo.setText(colaborador.getCargo());

		TextView area = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_area_value);
		area.setText(colaborador.getArea());

		TextView email = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_email);
		email.setText(colaborador.getEmail());

		TextView anexo = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_anexo);
		anexo.setText(colaborador.getAnexo());

		TextView puesto = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_puesto_value);
		puesto.setText(colaborador.getPuesto());
	}

	public void probarDeserializacionGSON() {
		String result = "{\"respuesta\":\"1\",\"jefeEquipo\":{\"nombreCompleto\":\"Juan Perez\",\"cargo\":\"Jefe de �rea\",\"area\":\"Logistica y Operaciones\",\"puesto\":\"Subgerente de An�lisis\",\"anexo\":\"2891\",\"email\":\"jperez@rhpp.com\",\"cantidadSubordinados\":\"3\",\"listaSubordinados\":{\"colaborador1\":{\"nombreCompleto\":\"Carla Sanchez\",\"cargo\":\"xxxxx\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"2\",\"listaSubordinados\":{\"subcolaborador1\":{\"nombreCompleto\":\"practicante1A\",\"cargo\":\"xxxxx\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}},\"subcolaborador2\":{\"nombreCompleto\":\"practicante2A\",\"cargo\":\"xxxxx\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}},\"colaborador2\":{\"nombreCompleto\":\"Mateo Soto\",\"cargo\":\"xxxxx\",	\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"1\",\"listaSubordinados\":{\"subcolaborador1\":{\"nombreCompleto\":\"practicante1B\",\"cargo\":\"xxxxx\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}},\"colaborador3\":{\"nombreCompleto\":\"Diego Bernal\",\"cargo\":\"xxxxx\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}}}";
		// System.out.println("Recibido: " + result.toString());
		// deserializando el json parte por parte
		try {
			JSONObject jsonObject = new JSONObject(result);
			String respuesta = jsonObject.getString("respuesta");
			if (procesaRespuesta(respuesta)) {
				System.out.println("procesa respuesta");
				// Obtenemos el jefe de todos --NIVEL 0
				JSONObject jefeObject = (JSONObject) jsonObject
						.get("jefeEquipo");
				jefe = new ColaboradorEquipoTrabajo(
						jefeObject.getString("nombreCompleto"),
						jefeObject.getString("cargo"),
						jefeObject.getString("area"),
						jefeObject.getString("puesto"),
						jefeObject.getString("anexo"),
						jefeObject.getString("email"),
						jefeObject.getInt("cantidadSubordinados"));
				System.out.println(jefe.toString());
				// Obtenemos la lista de subordinados del jefe de todos --NIVEL
				// 1
				JSONObject listaSubordinadosNivel1 = (JSONObject) jefeObject
						.get("listaSubordinados");

				// Para cada uno de los subordinados del jefe de todos
				JSONObject subordinadoNivel1Object;
				padres = new ArrayList<ColaboradorEquipoTrabajo>();
				hijos = new ArrayList<ArrayList<ArrayList<ColaboradorEquipoTrabajo>>>();
				for (int i = 1; i <= jefe.getCantidadSubordinados(); i++) {
					subordinadoNivel1Object = (JSONObject) listaSubordinadosNivel1
							.get("colaborador" + String.valueOf(i));

					subordinadoNivel1 = new ColaboradorEquipoTrabajo(
							subordinadoNivel1Object.getString("nombreCompleto"),
							subordinadoNivel1Object.getString("cargo"),
							subordinadoNivel1Object.getString("area"),
							subordinadoNivel1Object.getString("puesto"),
							subordinadoNivel1Object.getString("anexo"),
							subordinadoNivel1Object.getString("email"),
							subordinadoNivel1Object
									.getInt("cantidadSubordinados"));

					padres.add(subordinadoNivel1);
					System.out.println(subordinadoNivel1.toString());
					JSONObject listaSubordinadosNivel2 = (JSONObject) subordinadoNivel1Object
							.get("listaSubordinados");

					JSONObject subordinadoNivel2Object;
					hijos.add(new ArrayList<ArrayList<ColaboradorEquipoTrabajo>>());
					// childs.add(new ArrayList<ArrayList<String>>());
					for (int j = 1; j <= subordinadoNivel1
							.getCantidadSubordinados(); j++) {
						subordinadoNivel2Object = (JSONObject) listaSubordinadosNivel2
								.get("subcolaborador" + String.valueOf(j));

						subordinadoNivel2 = new ColaboradorEquipoTrabajo(
								subordinadoNivel2Object
										.getString("nombreCompleto"),
								subordinadoNivel2Object.getString("cargo"),
								subordinadoNivel2Object.getString("area"),
								subordinadoNivel2Object.getString("puesto"),
								subordinadoNivel2Object.getString("anexo"),
								subordinadoNivel2Object.getString("email"),
								subordinadoNivel2Object
										.getInt("cantidadSubordinados"));

						hijos.get(i - 1).add(
								new ArrayList<ColaboradorEquipoTrabajo>());
						hijos.get(i - 1).get(j - 1).add(subordinadoNivel2);
						/*
						 * 
						 * childs.get(0).add(new ArrayList<String>());
						 * childs.get(0).get(0).add("practicante 1A");
						 * childs.get(0).add(new ArrayList<String>());
						 * childs.get(0).get(1).add("practicante 1B");
						 * 
						 * childs.add(new ArrayList<ArrayList<String>>());
						 * childs.get(1).add(new ArrayList<String>());
						 * childs.get(1).get(0).add("practicante 2B");
						 * 
						 * childs.add(new ArrayList<ArrayList<String>>());
						 */

						System.out.println(subordinadoNivel2.toString());
					}
				}

			}
		} catch (JSONException e) {
			System.out.println("entre al catch1");
			System.out.println(e.toString());
			mostrarErrorComunicacion(e.toString());
		} catch (NullPointerException ex) {
			System.out.println("entre al catch2");
			System.out.println(ex.toString());
			mostrarErrorComunicacion(ex.toString());
		}
	}

	private void loadData() {
		groups = new ArrayList<String>();
		childs = new ArrayList<ArrayList<ArrayList<String>>>();

		groups.add("Carla Sanchez");
		groups.add("Mateo Soto");
		groups.add("Diego Bernal");

		childs.add(new ArrayList<ArrayList<String>>());
		childs.get(0).add(new ArrayList<String>());
		childs.get(0).get(0).add("practicante 1A");
		childs.get(0).add(new ArrayList<String>());
		childs.get(0).get(1).add("practicante 1B");

		childs.add(new ArrayList<ArrayList<String>>());
		childs.get(1).add(new ArrayList<String>());
		childs.get(1).get(0).add("practicante 2B");

		childs.add(new ArrayList<ArrayList<String>>());
	}

	private void mostrarErrorComunicacion(String excepcion) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this
				.getActivity().getApplicationContext());
		builder.setTitle("Error de servicio");
		builder.setMessage("El servicio solicitado no est� disponible en el servidor: "
				+ excepcion.toString());
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();
	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (OPERACION_VALIDA.equals(respuestaServidor)) {
			return true;
		} else if (OPERACION_INVALIDA.equals(respuestaServidor)) {
			// Se muestra mensaje de usuario invalido
			AlertDialog.Builder builder = new AlertDialog.Builder(this
					.getActivity().getApplicationContext());
			builder.setTitle("Login inv�lido");
			builder.setMessage("Combinaci�n de usuario y/o contrase�a incorrectos.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return false;
		} else {
			// Se muestra mensaje de usuario invalido
			AlertDialog.Builder builder = new AlertDialog.Builder(this
					.getActivity().getApplicationContext());
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
