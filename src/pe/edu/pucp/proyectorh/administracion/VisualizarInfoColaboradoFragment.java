package pe.edu.pucp.proyectorh.administracion;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

public class VisualizarInfoColaboradoFragment extends Fragment {
	// public FragmentActivity activity;
	public View rootView;
	
	public VisualizarInfoColaboradoFragment() {

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
		this.rootView = inflater.inflate(R.layout.visualizar_info_colaborador,
				container, false);

		System.out.println("el usuario es: " + LoginActivity.idUsuario);
		//llamarServicioInfoColaborador(LoginActivity.idUsuario);
		//XD();
		return rootView;
	}

	private void llamarServicioInfoColaborador(String usuario) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.InformacionPersonalService + "?username="
					+ usuario;
			System.out.println("pagina: " +request);
			new InformacionPersonalColaborador().execute(request);
		} else {
			// Se muestra mensaje de error de conexion con el servicio
			/*
			 * AlertDialog.Builder builder = new AlertDialog.Builder(this);
			 * builder.setTitle("Error de conexción"); builder.setMessage(
			 * "No se pudo conectar con el servidor. Revise su conexión a Internet."
			 * ); builder.setCancelable(false); builder.setPositiveButton("Ok",
			 * null); builder.create(); builder.show();
			 */
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

	public class InfoColaborador {
		private String nombres;
		private String apellidos;
		private String area;
		private String puesto;
		private String email;
		private String anexo;
		private String fecha_ingreso;

		public InfoColaborador() {
		}

		public String getNombres() {
			return nombres;
		}

		public void setNombres(String nombres) {
			this.nombres = nombres;
		}

		public String getApellidos() {
			return apellidos;
		}

		public void setApellidos(String apellidos) {
			this.apellidos = apellidos;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getPuesto() {
			return puesto;
		}

		public void setPuesto(String puesto) {
			this.puesto = puesto;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getAnexo() {
			return anexo;
		}

		public void setAnexo(String anexo) {
			this.anexo = anexo;
		}

		public String getFecha_ingreso() {
			return fecha_ingreso;
		}

		public void setFecha_ingreso(String fecha_ingreso) {
			this.fecha_ingreso = fecha_ingreso;
		}
				
	}

	/*
	 * lo que tienes que hacer es obtienes el elemento (ejemplo: textview1)
	 * textView1.setText(colaborador.getNombre()); esa es la idea todo al crear
	 * la actividad en onCreate uno de esos métodos que inicializa el fragment
	 */

	public void procesarInformacion(InfoColaborador infoColaborador) {		
		TextView text = (TextView) this.rootView.findViewById(R.id.mi_info_input_nombres);		
		text.setText(infoColaborador.getNombres());
	}
	
	public void XD() {		
		TextView text = (TextView) this.rootView.findViewById(R.id.mi_info_input_nombres);		
		text.setText("xD");
	}
}
