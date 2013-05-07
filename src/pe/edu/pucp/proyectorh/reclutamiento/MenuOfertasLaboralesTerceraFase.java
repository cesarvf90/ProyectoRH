package pe.edu.pucp.proyectorh.reclutamiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Area;
import pe.edu.pucp.proyectorh.model.OfertaLaboral;
import pe.edu.pucp.proyectorh.model.Postulante;
import pe.edu.pucp.proyectorh.model.Puesto;
import pe.edu.pucp.proyectorh.utils.OfertasAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class MenuOfertasLaboralesTerceraFase extends Fragment {

	private View rootView;

	// private ArrayList<OfertaLaboral> ofertas;
	// private ArrayList<ArrayList<ArrayList<Postulante>>> postulantes;

	public MenuOfertasLaboralesTerceraFase() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.menu_ofertas_tercera_fase,
				container, false);
		llamarServicioOfertasLaboralesTerceraFase();
		return rootView;
	}

	private void llamarServicioOfertasLaboralesTerceraFase() {
		// TODO cvasquez: llamar al servicio que devuelve las ofertas laborales
		// que se encuentran en tercera fase

		// Se genera data mock

		// fechas
		Date fecha1 = new Date(2012, 3, 7);
		Date fecha2 = new Date(1990, 11, 18);
		Date fecha3 = new Date(1986, 5, 17);
		Date fecha4 = new Date(2011, 10, 25);
		Date fecha5 = new Date(1982, 7, 11);
		Date fecha6 = new Date(2013, 3, 4);

		// postulantes
		ArrayList<Postulante> postulantes1 = new ArrayList<Postulante>();
		Postulante postulante1 = new Postulante("José", "Castillo");
		Postulante postulante2 = new Postulante("Bruce", "Dallas");
		Postulante postulante3 = new Postulante("Esteban", "Juarez");
		postulantes1.add(postulante1);
		postulantes1.add(postulante2);
		postulantes1.add(postulante3);

		// ofertas
		ArrayList<OfertaLaboral> ofertas = new ArrayList<OfertaLaboral>();
		Puesto puesto1 = new Puesto(new Area("Tecnología"),
				"Ingeniero de Software");
		Puesto puesto2 = new Puesto(new Area("Ventas"), "Ejecutivo");
		Puesto puesto3 = new Puesto(new Area("Marketing"),
				"Analista de tendencias");
		Puesto puesto4 = new Puesto(new Area("RRHH"), "Practicante");
		OfertaLaboral oferta1 = new OfertaLaboral(puesto1, postulantes1,
				"César Legario", fecha1, fecha6, "Entrevista con jefe");
		OfertaLaboral oferta2 = new OfertaLaboral(puesto2, postulantes1,
				"Gustavo López", fecha2, fecha3, "Entrevista con jefe");
		OfertaLaboral oferta3 = new OfertaLaboral(puesto3, postulantes1,
				"Margarita Reyes", fecha5, fecha3, "Entrevista con jefe");
		OfertaLaboral oferta4 = new OfertaLaboral(puesto4, postulantes1,
				"Stephano Dalma", fecha4, fecha5, "Entrevista con jefe");
		ofertas.add(oferta1);
		ofertas.add(oferta2);
		ofertas.add(oferta3);
		ofertas.add(oferta4);

		ArrayList<ArrayList<ArrayList<Postulante>>> postulantes = new ArrayList<ArrayList<ArrayList<Postulante>>>();
		postulantes.add(new ArrayList<ArrayList<Postulante>>());
		postulantes.get(0).add(new ArrayList<Postulante>());
		postulantes.get(0).get(0).add(postulantes1.get(0));
		postulantes.get(0).add(new ArrayList<Postulante>());
		postulantes.get(0).get(1).add(postulantes1.get(1));
		postulantes.get(0).add(new ArrayList<Postulante>());
		postulantes.get(0).get(2).add(postulantes1.get(2));

		postulantes.add(new ArrayList<ArrayList<Postulante>>());
		postulantes.get(1).add(new ArrayList<Postulante>());
		postulantes.get(1).get(0).add(new Postulante("Karina", "Olivos"));
		postulantes.get(1).add(new ArrayList<Postulante>());
		postulantes.get(1).get(1).add(new Postulante("Verónica", "Lasalle"));
		postulantes.get(1).add(new ArrayList<Postulante>());
		postulantes.get(1).get(2).add(new Postulante("Arturo", "Díaz"));

		postulantes.add(new ArrayList<ArrayList<Postulante>>());
		postulantes.get(2).add(new ArrayList<Postulante>());
		postulantes.get(2).get(0).add(new Postulante("Renzo", "Piaggio"));
		postulantes.get(2).add(new ArrayList<Postulante>());
		postulantes.get(2).get(1).add(new Postulante("Carla", "Solorzano"));
		postulantes.get(2).add(new ArrayList<Postulante>());
		postulantes.get(2).get(2).add(new Postulante("Angelica", "Goyzueta"));
		mostrarOfertasLaborales(ofertas, postulantes);

		postulantes.add(new ArrayList<ArrayList<Postulante>>());
		postulantes.get(3).add(new ArrayList<Postulante>());
		postulantes.get(3).get(0).add(new Postulante("Renzo", "Piaggio"));
		postulantes.get(3).add(new ArrayList<Postulante>());
		postulantes.get(3).get(1).add(new Postulante("Carla", "Solorzano"));
		postulantes.get(3).add(new ArrayList<Postulante>());
		postulantes.get(3).get(2).add(new Postulante("Angelica", "Goyzueta"));
		mostrarOfertasLaborales(ofertas, postulantes);
	}

	private void mostrarOfertasLaborales(ArrayList<OfertaLaboral> ofertas,
			ArrayList<ArrayList<ArrayList<Postulante>>> postulantes) {
		// TODO cvasquez: pinta las ofertas en los controles correspondientes
		// for (int i = 0; i < ofertas.size(); i++) {
		// ofertas.add(oferta);
		// postulantes.add(new ArrayList<ArrayList<Postulante>>());
		// for (int j = 0; j < oferta.getPostulantes().size(); ++j) {
		// postulantes.get(i).add(new ArrayList<Postulante>());
		// postulantes.get(i).get(j).add(oferta.getPostulantes().get(j));
		// }
		// }
		final ArrayList<OfertaLaboral> ofertasList = ofertas;
		ExpandableListView listaOfertas = (ExpandableListView) rootView
				.findViewById(R.id.evaluar_lista_ofertas);
		OfertasAdapter adapter = new OfertasAdapter(this.getActivity()
				.getApplicationContext(), ofertas, postulantes);
		listaOfertas.setAdapter(adapter);
		listaOfertas.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				System.out.println("Grupo " + (groupPosition));

				mostrarOfertaSeleccionada(ofertasList.get(groupPosition));
				return false;
			}
		});
	}

	protected void mostrarOfertaSeleccionada(OfertaLaboral oferta) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat();
		formatoFecha.applyPattern("dd/MM/yyyy");

		TextView tituloOfertaText = (TextView) rootView
				.findViewById(R.id.detalleofertas_title);
		tituloOfertaText.setText(oferta.toString());
		TextView puestoText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_puesto);
		puestoText.setText(oferta.getPuesto().getNombre());
		TextView areaText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_area);
		areaText.setText(oferta.getPuesto().getArea().getNombre());
		TextView solicitanteText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_solicitante);
		solicitanteText.setText(oferta.getSolicitante());
		TextView fechaSolicitudText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_fechasolicitud);
		fechaSolicitudText.setText(formatoFecha.format(oferta
				.getFechaRequerimiento()));
		TextView faseActualText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_faseactual);
		faseActualText.setText(oferta.getFaseActual());
		TextView numeroPostulantesText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_numeropostulantes);
		numeroPostulantesText.setText(String.valueOf(oferta.getPostulantes()
				.size()));
		TextView fechaUltimaEntrevistaText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_fecultentrevista);
		fechaUltimaEntrevistaText.setText(formatoFecha.format(oferta
				.getFechaUltimaEntrevista()));
	}
}
