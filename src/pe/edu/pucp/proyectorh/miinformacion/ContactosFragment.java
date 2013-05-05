package pe.edu.pucp.proyectorh.miinformacion;

import pe.edu.pucp.proyectorh.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactosFragment extends Fragment {

	private View rootView;

	public ContactosFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.mi_info_contactos, container,
				false);
		llamarServicioContactos();
		return rootView;
	}

	private void llamarServicioContactos() {
		// TODO cvasquez: llamar al servicio que devuelve los contactos del
		// usuario

		mostrarContactos();
	}

	private void mostrarContactos() {
		// TODO cvasquez: pinta los contactos en los controles correspondientes
		ListView listaContactos = (ListView) rootView
				.findViewById(R.id.mi_info_contactos_list);
		Colaborador[] colaboradores = {
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente"),
				new Colaborador("César", "Vásquez Flores",
						"Tecnologías de Información", "Gerente") };
		ArrayAdapter<Colaborador> colaboradoresAdapter = new ArrayAdapter<Colaborador>(
				this.getActivity(), android.R.layout.simple_list_item_1,
				colaboradores);
		listaContactos.setAdapter(colaboradoresAdapter);
	}

	public class Colaborador {

		private String nombres;
		private String apellidos;
		private String area;
		private String puesto;
		private String email;
		private String anexo;
		private String fechaIngreso;

		public Colaborador() {
		}

		public Colaborador(String nombres, String apellidos, String area,
				String puesto) {
			super();
			this.nombres = nombres;
			this.apellidos = apellidos;
			this.area = area;
			this.puesto = puesto;
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

		public String getFechaIngreso() {
			return fechaIngreso;
		}

		public void setFechaIngreso(String fechaIngreso) {
			this.fechaIngreso = fechaIngreso;
		}

	}

}
