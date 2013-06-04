package pe.edu.pucp.proyectorh;

import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad;
import pe.edu.pucp.proyectorh.objetivos.MisSubordinados;
import pe.edu.pucp.proyectorh.evaluacion360.RolEvaluador;
import pe.edu.pucp.proyectorh.miinformacion.AgendaFragment;
import pe.edu.pucp.proyectorh.miinformacion.ConsultarEquipoTrabajoFragment;
import pe.edu.pucp.proyectorh.miinformacion.ContactosFragment;
import pe.edu.pucp.proyectorh.miinformacion.VisualizarInfoColaboradoFragment;
import pe.edu.pucp.proyectorh.model.Modulo;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa;
import pe.edu.pucp.proyectorh.reclutamiento.AprobarSolicitudOfertaLaboral;
import pe.edu.pucp.proyectorh.reclutamiento.MenuOfertasLaboralesSegundaFase;
import pe.edu.pucp.proyectorh.reclutamiento.MenuOfertasLaboralesTerceraFase;
import pe.edu.pucp.proyectorh.reclutamiento.PostularOfertaLaboral;
import pe.edu.pucp.proyectorh.reportes.*;
import pe.edu.pucp.proyectorh.utils.Constante;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Actividad principal
 * 
 * @author Cesar
 * 
 */
public class MainActivity extends FragmentActivity implements
		MenuFragment.Callbacks {

	public static int NAVEGACION = 1;
	private boolean dosPaneles;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opcion_list);

		if (findViewById(R.id.opcion_detail_container) != null) {
			dosPaneles = true;
			((MenuFragment) getSupportFragmentManager().findFragmentById(
					R.id.opcion_list)).setActivateOnItemClick(true);
		}
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(29, 148, 59)));
		bar.setTitle("RH++");
	}

	@Override
	public void onItemSelected(String id) {
		if (dosPaneles) {
			if (Modulo.MODULO_ACTUAL == Constante.MI_INFORMACION) {
				if (id.equals("1")) { // Información personal
					VisualizarInfoColaboradoFragment fragment = new VisualizarInfoColaboradoFragment();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("2")) {// Mi equipo de trabajo
					ConsultarEquipoTrabajoFragment fragment = new ConsultarEquipoTrabajoFragment();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("3")) {// Mis contactos
					ContactosFragment fragment = new ContactosFragment();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("4")) {// Mi agenda
					AgendaFragment fragment = new AgendaFragment();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
			} else if (Modulo.MODULO_ACTUAL == Constante.RECLUTAMIENTO) {
				if (id.equals("1")) { // Aprobar Postulante

				}
				if (id.equals("2")) {// Aprobar Solicitudes Oferta Laboral
					AprobarSolicitudOfertaLaboral fragment = new AprobarSolicitudOfertaLaboral();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("3")) {// Aprobar Solicitudes Promoción

				}
				if (id.equals("4")) {// Evaluar Postulante 2da fase
					MenuOfertasLaboralesSegundaFase fragment = new MenuOfertasLaboralesSegundaFase();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("5")) {// Evaluar Postulante 3ra fase
					MenuOfertasLaboralesTerceraFase fragment = new MenuOfertasLaboralesTerceraFase();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("6")) {// Postular a Convocatoria
					PostularOfertaLaboral fragment = new PostularOfertaLaboral();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}

			} else if (Modulo.MODULO_ACTUAL == Constante.REPORTES) {

				if ("3".equals(id)) {
					ReporteCubrimientoPrincipal fragment = new ReporteCubrimientoPrincipal();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}

				if ("4".equals(id)) {
					ReporteObjetivosBSCPrincipal fragment = new ReporteObjetivosBSCPrincipal();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
			}

			else if (Modulo.MODULO_ACTUAL == Constante.EVALUACION_360) {
				if (id.equals("1")) { // Mis evaluaciones

				}
				if (id.equals("2")) {// Rol evaluadores
					RolEvaluador fragment = new RolEvaluador();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("3")) {// Mis subordinados
					pe.edu.pucp.proyectorh.evaluacion360.MisSubordinados fragment = new pe.edu.pucp.proyectorh.evaluacion360.MisSubordinados();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
			}

			else if (Modulo.MODULO_ACTUAL == Constante.LINEA_DE_CARRERA) {
				if (id.equals("1")) { // Comparar capacidades
					ComparaCapacidad fragment = new ComparaCapacidad();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("2")) {// Candidatos por puesto

				}

			} else if (Modulo.MODULO_ACTUAL == Constante.OBJETIVOS) {
				if (id.equals("1")) { // Objetivos Empresa
					ObjetivosEmpresa fragment = new ObjetivosEmpresa();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("2")) {// Mis Objetivos
					MisObjetivos fragment = new MisObjetivos();
					fragment.indicador = MisObjetivos.IND_MISOBJS;
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("3")) {// Objetivos Subordinados (PARA CREAR
										// OBJETIVOS PARA MIS SUBORDINADOS -
										// EVER)
					MisObjetivos fragment = new MisObjetivos();
					fragment.indicador = MisObjetivos.IND_SUBORD;
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("4")) {// Registrar Avance

				}
				if (id.equals("5")) {// Mis Avances

				}
				if (id.equals("6")) {// Monitoreo ( PARA VER LOS OBJETIVOS DE
										// MIS SUBORDINADOS - CARLOS)
				}
				if (id.equals("4")) {// Monitoreo ( PARA VER LOS OBJETIVOS DE MIS SUBORDINADOS -  CARLOS)

					MisSubordinados fragment = new MisSubordinados();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();

				}
			} else {
				Bundle arguments = new Bundle();
				arguments.putString(DetalleFragment.ARG_ITEM_ID, id);
				DetalleFragment fragment = new DetalleFragment();
				fragment.setArguments(arguments);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.opcion_detail_container, fragment)
						.commit();
			}
		} else {
			Intent detailIntent = new Intent(this, DetalleActivity.class);
			detailIntent.putExtra(DetalleFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Cerrar sesión");
			builder.setMessage("¿Está seguro que desea cerrar sesión?");
			builder.setCancelable(false);
			builder.setNegativeButton("Cancelar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.setPositiveButton("Aceptar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							cerrarSesion();
						}
					});
			builder.create();
			builder.show();
			return false;
		case R.id.help:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * amontoya: no modificar esta funcion Override public void onBackPressed()
	 * { }
	 */

	protected void cerrarSesion() {
		NAVEGACION = 1;
		finish();
	}
}
