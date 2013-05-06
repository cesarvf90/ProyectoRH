package pe.edu.pucp.proyectorh.miinformacion;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Colaborador;
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
				.findViewById(R.id.mi_info_lista_contactos);
		final ArrayList<Colaborador> contactos = new ArrayList<Colaborador>();
		Colaborador colaborador1 = new Colaborador("César", "Vásquez Flores",
				"Tecnología", "Gerente");
		Colaborador colaborador2 = new Colaborador("Yordy", "Reyna Serna",
				"Ventas", "Analista");
		Colaborador colaborador3 = new Colaborador("Claudia", "Montero Reyes",
				"Logística", "Jefe de Logística");
		for (int i = 0; i < 7; ++i) {
			contactos.add(colaborador1);
			contactos.add(colaborador2);
			contactos.add(colaborador3);
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
		TextView tituloContactoText = (TextView) rootView
				.findViewById(R.id.detalleContacto_title);
		tituloContactoText.setText(colaborador.toString());
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

	

}
