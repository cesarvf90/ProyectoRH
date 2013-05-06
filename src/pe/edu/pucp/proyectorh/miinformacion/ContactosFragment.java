package pe.edu.pucp.proyectorh.miinformacion;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.utils.Constante;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
		final ArrayList<Colaborador> contactos = new ArrayList<Colaborador>();
		Colaborador colaborador = new Colaborador("César", "Vásquez Flores",
				"Tecnologías de Información", "Gerente");
		for (int i = 0; i < 20; ++i) {
			contactos.add(colaborador);
		}
		ArrayAdapter<Colaborador> colaboradoresAdapter = new ArrayAdapter<Colaborador>(
				this.getActivity(), android.R.layout.simple_list_item_1,
				contactos);
		listaContactos.setAdapter(colaboradoresAdapter);
		listaContactos
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					public void onItemClick(AdapterView<?> parent,
							View childView, int position, long id) {
						// position tiene la posicion de la vista en el adapter
						mostrarContactoSeleccionado(contactos.get(position));
					}

					// TODO cvasquez: implementar llamada o envio de correo
					// onItemLongClick
				});
	}

	protected void mostrarContactoSeleccionado(Colaborador colaborador) {
		// TODO cvasquez: mostrar conctacto seleccionado en la lista
		TextView nombreText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_nombre);
		nombreText.setText(colaborador.getNombres());
		TextView apellidosText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_apellidos);
		apellidosText.setText(colaborador.getApellidos());
		TextView areaText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_area);
		areaText.setText(colaborador.getArea());
		TextView puestoText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_puesto);
		puestoText.setText(colaborador.getPuesto());
		TextView fechaNacimientoText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_fecnacimiento);
		fechaNacimientoText.setText(colaborador.getFechaNacimiento());
		TextView fechaIngresoText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_fecingreso);
		fechaIngresoText.setText(colaborador.getFechaIngreso());
		TextView correoElectronicoText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_correo);
		correoElectronicoText.setText(colaborador.getCorreoElectronico());
	}

	/**
	 * @author Cesar
	 * 
	 */
	public class Colaborador {

		private String nombres;
		private String apellidos;
		private String area;
		private String puesto;
		private String email;
		private String anexo;
		private String fechaIngreso;
		private String fechaNacimiento;
		private String correoElectronico;
		private String telefono;

		public String getFechaNacimiento() {
			return fechaNacimiento;
		}

		public void setFechaNacimiento(String fechaNacimiento) {
			this.fechaNacimiento = fechaNacimiento;
		}

		public String getCorreoElectronico() {
			return correoElectronico;
		}

		public void setCorreoElectronico(String correoElectronico) {
			this.correoElectronico = correoElectronico;
		}

		public String getTelefono() {
			return telefono;
		}

		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}

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

		@Override
		public String toString() {
			return nombres + Constante.ESPACIO_VACIO + apellidos;
		}
	}

}
