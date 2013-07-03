package pe.edu.pucp.proyectorh;

import pe.edu.pucp.proyectorh.lineadecarrera.CandidatosxPuesto;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad;
import pe.edu.pucp.proyectorh.objetivos.MisSubordinados;
import pe.edu.pucp.proyectorh.connection.DataBaseContactosConnection;
import pe.edu.pucp.proyectorh.evaluacion360.RolEvaluado;
import pe.edu.pucp.proyectorh.evaluacion360.RolEvaluadoEver;
import pe.edu.pucp.proyectorh.evaluacion360.RolEvaluador;
import pe.edu.pucp.proyectorh.miinformacion.AgendaFragment;
import pe.edu.pucp.proyectorh.miinformacion.ConsultarEquipoTrabajoFragment;
import pe.edu.pucp.proyectorh.miinformacion.ContactosFragment;
import pe.edu.pucp.proyectorh.miinformacion.VisualizarInfoColaboradoFragment;
import pe.edu.pucp.proyectorh.model.Modulo;
import pe.edu.pucp.proyectorh.model.Modulo.ModuloItem;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos;
import pe.edu.pucp.proyectorh.objetivos.Monitoreo;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa;
import pe.edu.pucp.proyectorh.objetivos.RegistroAvance;
import pe.edu.pucp.proyectorh.objetivos.VisualizacionAvance;
import pe.edu.pucp.proyectorh.reclutamiento.AprobarSolicitudOfertaLaboral;
import pe.edu.pucp.proyectorh.reclutamiento.MenuOfertasLaboralesPrimeraFase;
import pe.edu.pucp.proyectorh.reclutamiento.MenuOfertasLaboralesTerceraFase;
import pe.edu.pucp.proyectorh.reclutamiento.PostularOfertaLaboral;
import pe.edu.pucp.proyectorh.reportes.*;
import pe.edu.pucp.proyectorh.utils.Constante;
import pe.edu.pucp.proyectorh.utils.EstiloApp;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
		bar.setTitle("Usuario: " + LoginActivity.getUsuario().getUsername());
	}

	private void customizarEstilos(Context context, View view) {
		try {
			if (view instanceof ViewGroup) {
				ViewGroup vg = (ViewGroup) view;
				for (int i = 0; i < vg.getChildCount(); i++) {
					View child = vg.getChildAt(i);
					customizarEstilos(context, child);
				}
			} else if (view instanceof TextView) {
				((TextView) view).setTypeface(Typeface.createFromAsset(
						context.getAssets(), EstiloApp.FORMATO_LETRA_APP));
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void onItemSelected(String id) {
		if (dosPaneles) {
			if (Modulo.MODULO_ACTUAL == Constante.MI_INFORMACION) {
				if (Modulo.INFORMACION_PERSONAL.equals(id)) { // Información
																// personal
					VisualizarInfoColaboradoFragment fragment = new VisualizarInfoColaboradoFragment();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.EQUIPO_TRABAJO.equals(id)) {// Mi equipo de trabajo
					ConsultarEquipoTrabajoFragment fragment = new ConsultarEquipoTrabajoFragment();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.CONTACTOS.equals(id)) {// Mis contactos
					ContactosFragment fragment = new ContactosFragment();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.AGENDA.equals(id)) {// Mi agenda
					AgendaFragment fragment = new AgendaFragment();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
			} else if (Modulo.MODULO_ACTUAL == Constante.RECLUTAMIENTO) {
				if (Modulo.APROBAR_SOLICITUDES.equals(id)) {// Aprobar
															// Solicitudes
															// Oferta Laboral
					AprobarSolicitudOfertaLaboral fragment = new AprobarSolicitudOfertaLaboral();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.EVALUACION_FASE1.equals(id)) {// Evaluar Postulante
															// 1ra fase
					MenuOfertasLaboralesPrimeraFase fragment = new MenuOfertasLaboralesPrimeraFase();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.EVALUACION_FASE3.equals(id)) {// Evaluar Postulante
															// 3ra fase
					MenuOfertasLaboralesTerceraFase fragment = new MenuOfertasLaboralesTerceraFase();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.POSTULAR_OFERTA.equals(id)) {// Postular a
														// Convocatoria
					PostularOfertaLaboral fragment = new PostularOfertaLaboral();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}

			} else if (Modulo.MODULO_ACTUAL == Constante.REPORTES) {

				if (Modulo.REPORTE_EVALUACION_360.equals(id)) {
					Reporte360Grafico fragment = new Reporte360Grafico();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}

				if (Modulo.REPORTE_HISTORICO_OBJETIVOS.equals(id)) {
					ReportePersonalBSCPrincipal fragment = new ReportePersonalBSCPrincipal();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}

				if (Modulo.REPORTE_OFERTAS_LABORALES.equals(id)) {
					ReporteCubrimientoPrincipal fragment = new ReporteCubrimientoPrincipal();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}

				if (Modulo.REPORTE_OBJETIVOS_BSC.equals(id)) {
					ReporteObjetivosBSCPrincipal fragment = new ReporteObjetivosBSCPrincipal();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
			}

			else if (Modulo.MODULO_ACTUAL == Constante.EVALUACION_360) { // SE
																			// ACTUALIZO
				if (Modulo.PENDIENTES.equals(id)) {// Rol evaluadores
					RolEvaluador fragment = new RolEvaluador();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.ROL_EVALUADO.equals(id)) {// Rol de Evaluado
					RolEvaluadoEver fragment = new RolEvaluadoEver();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.SUBORDINADOS.equals(id)) {// Mis subordinados
					pe.edu.pucp.proyectorh.evaluacion360.MisSubordinados fragment = new pe.edu.pucp.proyectorh.evaluacion360.MisSubordinados();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
			}

			else if (Modulo.MODULO_ACTUAL == Constante.LINEA_DE_CARRERA) {
				if (Modulo.COMPARAR_CAPACIDADES.equals(id)) { // Comparar
																// capacidades
					ComparaCapacidad fragment = new ComparaCapacidad();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.CANDIDATOS_X_PUESTO.equals(id)) {// Candidatos por
															// puesto
					CandidatosxPuesto fragment = new CandidatosxPuesto();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}

			} else if (Modulo.MODULO_ACTUAL == Constante.OBJETIVOS) {
				if (Modulo.OBJETIVOS_EMPRESARIALES.equals(id)) { // Objetivos
																	// Empresa
					ObjetivosEmpresa fragment = new ObjetivosEmpresa();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.OBJETIVOS_PROPIOS.equals(id)) {// Mis Objetivos
					MisObjetivos fragment = new MisObjetivos();
					fragment.indicador = MisObjetivos.IND_MISOBJS;
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (Modulo.OBJETIVOS_EQUIPO.equals(id)) {// Objetivos
															// Subordinados
															// (PARA CREAR
					// OBJETIVOS PARA MIS SUBORDINADOS -
					// EVER)
					MisObjetivos fragment = new MisObjetivos();
					fragment.indicador = MisObjetivos.IND_SUBORD;
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}

				if (Modulo.REGISTRAR_AVANCE.equals(id)) {// Registrar Avance
					RegistroAvance fragment = new RegistroAvance();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();

				}
				if (Modulo.VISUALIZAR_AVANCE.equals(id)) {// Mis Avances
					VisualizacionAvance fragment = new VisualizacionAvance();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();

				}
				if (Modulo.MONITOREO.equals(id)) {// Monitoreo ( PARA VER LOS
													// OBJETIVOS DE
					// MIS SUBORDINADOS - CARLOS)
					Monitoreo fragment = new Monitoreo();
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
		borrarDatosPersistidos();
		finish();
	}

	private void borrarDatosPersistidos() {
		deleteDatabase(DataBaseContactosConnection.obtenerNombre());
	}
}
