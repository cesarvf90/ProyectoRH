package pe.edu.pucp.proyectorh.administracion;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.InfoColaborador;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;

public class ConsultarEquipoTrabajoFragment extends Fragment {

	private View rootView;
	private ExpandableListView exv;
	private ArrayList<String> groups;
	private ArrayList<ArrayList<ArrayList<String>>> childs;

	public ConsultarEquipoTrabajoFragment() {

	}

	/*
	 * public VisualizarInfoColaboradoFragment(FragmentActivity _activity) {
	 * this.activity = _activity; }
	 */

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
		// exv.setAdapter(new
		// AdapterEquipoTrabajo(this.getActivity().getApplicationContext()));

		loadData();
		MyExpandableAdapter adapter = new MyExpandableAdapter(this
				.getActivity().getApplicationContext(), groups, childs);
		exv.setAdapter(adapter);

		// llamarServicioConsultarEquipoTrabajo(LoginActivity.idUsuario);
		// probarDeserializacionGSON();
		return rootView;
	}

	private void llamarServicioConsultarEquipoTrabajo(String usuario) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.InformacionPersonalService + "?username="
					+ usuario;
			System.out.println("pagina: " + request);
			new InformacionPersonalColaborador().execute(request);
		}
	}

	public class InformacionPersonalColaborador extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			// Log.i(LoginUsuario.class.getName(),
			// "Recibido: " + result.toString());
			System.out.println("Recibido: " + result.toString());

			final Gson gson = new Gson();
			final InfoColaborador infoColaborador = gson.fromJson(result,
					InfoColaborador.class);
			procesarInformacion(infoColaborador);
		}
	}

	public void procesarInformacion(InfoColaborador infoColaborador) {
		TextView nombres = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_nombres);
		nombres.setText(infoColaborador.getNombres());

		TextView apellidos = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_apellidos);
		apellidos.setText(infoColaborador.getApellidos());

		TextView area = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_area);
		area.setText(infoColaborador.getArea());

		TextView puesto = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_puesto);
		puesto.setText(infoColaborador.getPuesto());

		TextView email = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_email);
		email.setText(infoColaborador.getEmail());

		TextView anexo = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_telefono);
		anexo.setText(infoColaborador.getAnexo());

		TextView fecha_ingreso = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_fechaingreso);
		fecha_ingreso.setText(infoColaborador.getFecha_ingreso());
	}

	public void probarDeserializacionGSON() {
		/*
		 * private String nombres; private String apellidos; private String
		 * area; private String puesto; private String email; private String
		 * anexo; private String fecha_ingreso;
		 */
		String result = "{\"nombres\":\"Fortino Mario Alonso\",\"apellidos\":\"Moreno Reyes\",\"area\":\"Marketing\",\"puesto\":\"Analista de Productos\",\"email\":\"m.reyes@rhpp.com\",\"anexo\":\"3302\",\"fecha_ingreso\":\"05/01/2011\"}";
		System.out.println("Recibido: " + result.toString());
		final Gson gson = new Gson();
		final InfoColaborador infoColaborador = gson.fromJson(result,
				InfoColaborador.class);

		TextView nombres = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_nombres);
		nombres.setText(infoColaborador.getNombres());

		TextView apellidos = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_apellidos);
		apellidos.setText(infoColaborador.getApellidos());

		TextView area = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_area);
		area.setText(infoColaborador.getArea());

		TextView puesto = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_puesto);
		puesto.setText(infoColaborador.getPuesto());

		TextView email = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_email);
		email.setText(infoColaborador.getEmail());

		TextView anexo = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_telefono);
		anexo.setText(infoColaborador.getAnexo());

		TextView fecha_ingreso = (TextView) this.rootView
				.findViewById(R.id.mi_info_input_fechaingreso);
		fecha_ingreso.setText(infoColaborador.getFecha_ingreso());
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

}
