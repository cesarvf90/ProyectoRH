package pe.edu.pucp.proyectorh.miinformacion;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.ColaboradorEquipoTrabajo;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

public class ConsultarEquipoTrabajoFragment extends Fragment {

	private static final String OPERACION_VALIDA = "true";
	private static final String OPERACION_INVALIDA = "false";
	private View rootView;
	private View layoutVacio;
	private ExpandableListView exv;
	private ArrayList<String> groups;
	private ArrayList<ArrayList<ArrayList<String>>> childs;
	private ArrayList<ColaboradorEquipoTrabajo> padres;
	private ArrayList<ArrayList<ArrayList<ColaboradorEquipoTrabajo>>> hijos;
	private ColaboradorEquipoTrabajo colaborador;
	private ColaboradorEquipoTrabajo jefe;
	private ColaboradorEquipoTrabajo subordinadoNivel1;
	private ColaboradorEquipoTrabajo subordinadoNivel2;
	private TextView txtVJefe;

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
		txtVJefe = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_cabecera_jefe);

		// loadData();

		if ((LoginActivity.usuario.getPadres() == null)
				|| (LoginActivity.usuario.getHijos() == null)
				|| (LoginActivity.usuario.getJefe() == null)) {
			System.out.println("Primera vez: llamamos al WS");
			probarDeserializacionJSON("");
			// llamarServicioConsultarEquipoTrabajo(LoginActivity.usuario.getID());
			LoginActivity.usuario.setJefe(this.jefe);
			LoginActivity.usuario.setHijos(this.hijos);
			LoginActivity.usuario.setPadres(this.padres);
			this.padres = LoginActivity.usuario.getPadres();
			this.hijos = LoginActivity.usuario.getHijos();
			this.jefe = LoginActivity.usuario.getJefe();
		} else {
			System.out.println("Ya en memoria");
			this.padres = LoginActivity.usuario.getPadres();
			this.hijos = LoginActivity.usuario.getHijos();
			this.jefe = LoginActivity.usuario.getJefe();
		}

		// Si todo salió bien y logramos poblar todos los elementos, levantamos
		// la vista
		if ((this.jefe != null) && (this.padres != null)
				&& (this.hijos != null)) {
			TextView txtCabeceraJefe = (TextView) rootView
					.findViewById(R.id.equipo_trabajo_cabecera_jefe);
			txtCabeceraJefe.setText("Jefe de Equipo: "
					+ (jefe.getNombres() == "null" ? "" : jefe.getNombres())
					+ " "
					+ (jefe.getApellidoPaterno() == "null" ? "" : jefe
							.getApellidoPaterno())
					+ " "
					+ (jefe.getApellidoMaterno() == "null" ? "" : jefe
							.getApellidoMaterno()));

			/*
			 * MyExpandableAdapter adapter = new MyExpandableAdapter(this
			 * .getActivity().getApplicationContext(), groups, childs);
			 * exv.setAdapter(adapter);
			 */

			AdapterEquipoObjetosColaboradores adapter = new AdapterEquipoObjetosColaboradores(
					this.getActivity().getApplicationContext(), padres, hijos);
			exv.setAdapter(adapter);

			exv.setOnGroupClickListener(new OnGroupClickListener() {
				@Override
				public boolean onGroupClick(ExpandableListView parent, View v,
						int groupPosition, long id) {
					System.out.println("Grupo " + (groupPosition));

					mostrarDatosPadre(groupPosition);
					return false;
				}
			});

			exv.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					System.out.println("Grupo " + (groupPosition) + "Hijo "
							+ childPosition);
					mostrarDatosHijo(groupPosition, childPosition);
					return false;
				}
			});

			txtVJefe.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					pintarLadoDerecho(jefe);
				}
			});
			return rootView;
		} else {
			// Caso contrario, mostramos una vista vacía
			this.layoutVacio = inflater.inflate(
					R.layout.layout_vacio_para_errores, container, false);
			return layoutVacio;
		}
	}

	protected void mostrarDatosPadre(int groupPosition) {
		/*
		 * String e = groups.get(groupPosition); System.out.println(e);
		 */
		pintarLadoDerecho(padres.get(groupPosition));
	}

	protected void mostrarDatosHijo(int groupPosition, int childPosition) {
		/*
		 * String e = groups.get(groupPosition); System.out.println(e);
		 */
		pintarLadoDerecho(hijos.get(groupPosition).get(childPosition).get(0));
	}

	private void llamarServicioConsultarEquipoTrabajo(int idUsuario) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.InformacionPersonalService + "?username="
					+ idUsuario;
			System.out.println("pagina: " + request);
			new deserializarJSON().execute(request);
		}
	}

	public class deserializarJSON extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			// MISMA LOGICA QUE probarDeserializacionGSON
			probarDeserializacionJSON(result);
		}
	}

	public void pintarLadoDerecho(ColaboradorEquipoTrabajo colaborador) {
		TextView nombreCompleto = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_nombreCompleto);
		nombreCompleto.setText((colaborador.getNombres() == "null" ? ""
				: colaborador.getNombres())
				+ " "
				+ (colaborador.getApellidoPaterno() == "null" ? ""
						: colaborador.getApellidoPaterno())
				+ " "
				+ (colaborador.getApellidoMaterno() == "null" ? ""
						: colaborador.getApellidoMaterno()));

		TextView area = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_area_value);
		area.setText(colaborador.getArea() == "null" ? "* no disponible"
				: colaborador.getArea());

		TextView email = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_email);
		email.setText(colaborador.getEmail() == "null" ? "* no disponible"
				: colaborador.getEmail());

		TextView anexo = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_anexo);
		anexo.setText(colaborador.getAnexo() == "null" ? "* no disponible"
				: colaborador.getAnexo());

		TextView puesto = (TextView) this.rootView
				.findViewById(R.id.equipo_trabajo_puesto_value);
		puesto.setText(colaborador.getPuesto() == "null" ? "* no disponible"
				: colaborador.getPuesto());
	}

	public void probarDeserializacionJSON(String str) {
		String result;
		// "{\"respuesta\":\"1\",\"jefeEquipo\":{\"nombreCompleto\":\"CHRISTIAN MENDEZ\",\"area\":\"Logistica y Operaciones\",\"puesto\":\"Subgerente de Análisis\",\"anexo\":\"2891\",\"email\":\"jperez@rhpp.com\",\"cantidadSubordinados\":\"3\",\"listaSubordinados\":{\"colaborador1\":{\"nombreCompleto\":\"Carla Sanchez\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"2\",\"listaSubordinados\":{\"subcolaborador1\":{\"nombreCompleto\":\"practicante1A\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}},\"subcolaborador2\":{\"nombreCompleto\":\"practicante2A\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}},\"colaborador2\":{\"nombreCompleto\":\"Mateo Soto\",	\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"1\",\"listaSubordinados\":{\"subcolaborador1\":{\"nombreCompleto\":\"practicante1B\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}},\"colaborador3\":{\"nombreCompleto\":\"Diego Bernal\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}}}";
		if (str != "")
			result = str;
		else
			// cadena ejemplo antes de que manolin termine el ws
			// result =
			// "{\"respuesta\":\"1\",\"jefeEquipo\":{\"nombreCompleto\":\"big boss\",\"area\":\"xxx\",\"puesto\":\"xxx\",\"anexo\":\"xxx\",\"email\":\"xxx\",\"cantidadSubordinados\":\"2\",	\"listaSubordinados\":[{\"nombreCompleto\":\"nombre1\",\"area\":\"xxx\",\"puesto\":\"xxx\",\"anexo\":\"xxx\",\"email\":\"xxx\",\"cantidadSubordinados\":\"2\",\"listaSubordinados\":[{\"nombreCompleto\":\"nombre2\",\"area\":\"xxx\",\"puesto\":\"xxx\",\"anexo\":\"xxx\",\"email\":\"xxx\",\"cantidadSubordinados\":\"0\"},{\"nombreCompleto\":\"nombre3\",\"area\":\"xxx\",\"puesto\":\"xxx\",\"anexo\":\"xxx\",\"email\":\"xxx\",\"cantidadSubordinados\":\"0\"}]},{\"nombreCompleto\":\"nombre4\",\"area\":\"xxx\",\"puesto\":\"xxx\",\"anexo\":\"xxx\",\"email\":\"xxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":[]}]}}";
			// cadena real despues que manolin termino el ws
			result = "{\"success\":true,\"data\":{\"NombreCompleto\":\"Moreno Reyes, Fortino Mario Alonso\",\"ID\":2,\"Nombre\":\"Fortino Mario Alonso\",\"ApellidoPaterno\":\"Moreno\",\"ApellidoMaterno\":\"Reyes\",\"Telefono\":null,\"Direccion\":null,\"PaisID\":1,\"FechaNacimiento\":null,\"TipoDocumentoID\":2,\"NumeroDocumento\":null,\"Usuario\":null,\"EstadoColaboradorID\":1,\"CurriculumVitaeID\":0,\"ImagenColaboradorID\":0,\"CentroEstudios\":null,\"GradoAcademicoID\":1,\"CorreoElectronico\":null,\"FechaIngreso\":null,\"AreaID\":1,\"Area\":\"Directorio\",\"PuestoID\":1,\"Puesto\":\"Presidente\",\"Sueldo\":2300,\"ResumenEjecutivo\":null,\"Contrasenha\":null,\"NuevaContrasenha\":null,\"Objetivos\":[],\"Subordinados\":[{\"NombreCompleto\":\"Vega Buendía, Miguel\",\"ID\":3,\"Nombre\":\"Miguel\",\"ApellidoPaterno\":\"Vega\",\"ApellidoMaterno\":\"Buendía\",\"Telefono\":null,\"Direccion\":null,\"PaisID\":1,\"FechaNacimiento\":null,\"TipoDocumentoID\":2,\"NumeroDocumento\":null,\"Usuario\":null,\"EstadoColaboradorID\":1,\"CurriculumVitaeID\":0,\"ImagenColaboradorID\":0,\"CentroEstudios\":null,\"GradoAcademicoID\":1,\"CorreoElectronico\":null,\"FechaIngreso\":null,\"AreaID\":2,\"Area\":\"Gerencia general\",\"PuestoID\":2,\"Puesto\":\"Gerente general\",\"Sueldo\":2500,\"ResumenEjecutivo\":null,\"Contrasenha\":null,\"NuevaContrasenha\":null,\"Objetivos\":[],\"Subordinados\":[{\"NombreCompleto\":\"Chavez Moreno, Rodrigo\",\"ID\":23,\"Nombre\":\"Rodrigo\",\"ApellidoPaterno\":\"Chavez\",\"ApellidoMaterno\":\"Moreno\",\"Telefono\":null,\"Direccion\":null,\"PaisID\":1,\"FechaNacimiento\":null,\"TipoDocumentoID\":2,\"NumeroDocumento\":null,\"Usuario\":null,\"EstadoColaboradorID\":1,\"CurriculumVitaeID\":0,\"ImagenColaboradorID\":0,\"CentroEstudios\":null,\"GradoAcademicoID\":0,\"CorreoElectronico\":null,\"FechaIngreso\":null,\"AreaID\":6,\"Area\":\"Márketing\",\"PuestoID\":5,\"Puesto\":\"Gerente de márketing\",\"Sueldo\":2000,\"ResumenEjecutivo\":null,\"Contrasenha\":null,\"NuevaContrasenha\":null,\"Objetivos\":[{\"ID\":5,\"Nombre\":\"Aumentar las ventas\",\"Peso\":50,\"AvanceFinal\":10,\"TipoObjetivoBSCID\":0,\"ObjetivoPadreID\":1,\"BSCID\":1,\"FechaDePropuesta\":\"\",\"FechaFinalizacion\":\"\"},{\"ID\":6,\"Nombre\":\"Reducir los costos\",\"Peso\":25,\"AvanceFinal\":10,\"TipoObjetivoBSCID\":0,\"ObjetivoPadreID\":1,\"BSCID\":1,\"FechaDePropuesta\":\"\",\"FechaFinalizacion\":\"\"},{\"ID\":7,\"Nombre\":\"Ganar nuevos clientes\",\"Peso\":25,\"AvanceFinal\":10,\"TipoObjetivoBSCID\":0,\"ObjetivoPadreID\":1,\"BSCID\":1,\"FechaDePropuesta\":\"\",\"FechaFinalizacion\":\"\"}],\"Subordinados\":null}]}]}}";
		// System.out.println("Recibido: " + result.toString());
		// deserializando el json parte por parte
		try {
			JSONObject jsonObject = new JSONObject(result);
			System.out.println("result: " + result);
			String respuesta = jsonObject.getString("success");
			if (procesaRespuesta(respuesta)) {
				// System.out.println("respuesta: "+respuesta);
				// Obtenemos el jefe de todos --NIVEL 0
				JSONObject jefeObject = (JSONObject) jsonObject.get("data");
				jefe = new ColaboradorEquipoTrabajo(jefeObject.getString("ID"),
						jefeObject.getString("Nombre"),
						jefeObject.getString("ApellidoPaterno"),
						jefeObject.getString("ApellidoMaterno"),
						jefeObject.getString("Area"),
						jefeObject.getString("Puesto"),
						jefeObject.getString("Telefono"),
						jefeObject.getString("CorreoElectronico"));
				// jefeObject.getInt("cantidadSubordinados"));

				System.out.println(jefe.toString());
				// Obtenemos la lista de subordinados del jefe de todos --NIVEL
				// 1
				JSONArray listaSubordinadosNivel1 = (JSONArray) jefeObject
						.get("Subordinados");

				int m = listaSubordinadosNivel1.length();
				System.out.println("size: " + String.valueOf(m));
				System.out.println("listaSubordinadosNivel1: "
						+ listaSubordinadosNivel1.toString());

				// Para cada uno de los subordinados del jefe de todos
				JSONObject subordinadoNivel1Object;
				padres = new ArrayList<ColaboradorEquipoTrabajo>();
				hijos = new ArrayList<ArrayList<ArrayList<ColaboradorEquipoTrabajo>>>();
				for (int i = 0; i < listaSubordinadosNivel1.length(); i++) {
					subordinadoNivel1Object = listaSubordinadosNivel1
							.getJSONObject(i);

					subordinadoNivel1 = new ColaboradorEquipoTrabajo(
							subordinadoNivel1Object.getString("ID"),
							subordinadoNivel1Object.getString("Nombre"),
							subordinadoNivel1Object
									.getString("ApellidoPaterno"),
							subordinadoNivel1Object
									.getString("ApellidoMaterno"),
							subordinadoNivel1Object.getString("Area"),
							subordinadoNivel1Object.getString("Puesto"),
							subordinadoNivel1Object.getString("Telefono"),
							subordinadoNivel1Object
									.getString("CorreoElectronico"));
					// subordinadoNivel1Object.getInt("cantidadSubordinados"));

					// Si es distinto de la persona logueada (la que hace la
					// consulta) lo agregamos
					// if (subordinadoNivel1.getId() !=
					// LoginActivity.usuario.getID()) {
					padres.add(subordinadoNivel1);
					System.out.println(subordinadoNivel1.toString());
					JSONArray listaSubordinadosNivel2 = (JSONArray) subordinadoNivel1Object
							.get("Subordinados");

					m = listaSubordinadosNivel2.length();
					System.out.println("size2: " + String.valueOf(m));

					JSONObject subordinadoNivel2Object;
					hijos.add(new ArrayList<ArrayList<ColaboradorEquipoTrabajo>>());

					for (int j = 0; j < listaSubordinadosNivel2.length(); j++) {
						subordinadoNivel2Object = (JSONObject) listaSubordinadosNivel2
								.get(j);

						subordinadoNivel2 = new ColaboradorEquipoTrabajo(
								subordinadoNivel2Object.getString("ID"),
								subordinadoNivel2Object.getString("Nombre"),
								subordinadoNivel2Object
										.getString("ApellidoPaterno"),
								subordinadoNivel2Object
										.getString("ApellidoMaterno"),
								subordinadoNivel2Object.getString("Area"),
								subordinadoNivel2Object.getString("Puesto"),
								subordinadoNivel2Object.getString("Telefono"),
								subordinadoNivel2Object
										.getString("CorreoElectronico"));

						hijos.get(i).add(
								new ArrayList<ColaboradorEquipoTrabajo>());
						hijos.get(i).get(j).add(subordinadoNivel2);

						System.out.println(subordinadoNivel2.toString());

					}
				}
				// }
				System.out
						.println("***********************************************************************");
				System.out.println(padres.toString());
				System.out.println(hijos.toString());
			}
		} catch (JSONException e) {
			System.out.println("entre al catch1");
			System.out.println(e.toString());
			mostrarErrorComunicacion(e.toString());
			padres = null;
			hijos = null;
			jefe = null;
		} catch (NullPointerException ex) {
			System.out.println("entre al catch2");
			System.out.println(ex.toString());
			mostrarErrorComunicacion(ex.toString());
			padres = null;
			hijos = null;
			jefe = null;
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
		builder.setMessage("El servicio solicitado no está disponible en el servidor: "
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
			builder.setTitle("Login inválido");
			builder.setMessage("Combinación de usuario y/o contraseña incorrectos.");
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
