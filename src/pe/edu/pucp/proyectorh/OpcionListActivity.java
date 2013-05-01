package pe.edu.pucp.proyectorh;

import pe.edu.pucp.proyectorh.administracion.RendirEvaluacionesFragment;
import pe.edu.pucp.proyectorh.administracion.VisualizarInfoColaboradoFragment;
import pe.edu.pucp.proyectorh.model.Modulo;
import pe.edu.pucp.proyectorh.reportes.*;
import pe.edu.pucp.proyectorh.evaluacion360.*;
import pe.edu.pucp.proyectorh.objetivos.*;
import pe.edu.pucp.proyectorh.utils.Constante;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

/**
 * Actividad principal
 * 
 * @author Cesar
 * 
 */
public class OpcionListActivity extends FragmentActivity implements
		OpcionListFragment.Callbacks {

	public static int NAVEGACION = 1;
	private boolean dosPaneles;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opcion_list);

		if (findViewById(R.id.opcion_detail_container) != null) {
			dosPaneles = true;
			((OpcionListFragment) getSupportFragmentManager().findFragmentById(
					R.id.opcion_list)).setActivateOnItemClick(true);
		}
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

				}
				if (id.equals("3")) {// Mis contactos

				}
				if (id.equals("4")) {// Mi agenda

				}

			} else if ((Modulo.MODULO_ACTUAL == Constante.RECLUTAMIENTO)
					&& ("4".equals(id))) {
				RendirEvaluacionesFragment fragment = new RendirEvaluacionesFragment();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.opcion_detail_container, fragment)
						.commit();
			}

			else if ((Modulo.MODULO_ACTUAL == Constante.REPORTES)
					&& ("4".equals(id))) {
				ReporteObjetivosBSCPrincipal fragment = new ReporteObjetivosBSCPrincipal();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.opcion_detail_container, fragment)
						.commit();
			}

			else if (Modulo.MODULO_ACTUAL == Constante.EVALUACION_360) {
				if (id.equals("1")) { // Mis evaluaciones

				}
				if (id.equals("2")) {// Rol evaluadores

				}
				if (id.equals("3")) {// Mis subordinados

				}
			} else if (Modulo.MODULO_ACTUAL == Constante.OBJETIVOS) {
				if (id.equals("1")) { // Objetivos Empresa
					ObjetivosEmpresa fragment = new ObjetivosEmpresa();
					getSupportFragmentManager().beginTransaction()
							.replace(R.id.opcion_detail_container, fragment)
							.commit();
				}
				if (id.equals("2")) {// Mis Objetivos

				}
				if (id.equals("3")) {// Objetivos Subordinados

				}
				if (id.equals("4")) {// Registrar Avance

				}
				if (id.equals("5")) {// Mis Avances

				}
				if (id.equals("6")) {// Monitoreo

				}
			} else {
				Bundle arguments = new Bundle();
				arguments.putString(OpcionDetailFragment.ARG_ITEM_ID, id);
				OpcionDetailFragment fragment = new OpcionDetailFragment();
				fragment.setArguments(arguments);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.opcion_detail_container, fragment)
						.commit();
			}
		} else {
			Intent detailIntent = new Intent(this, OpcionDetailActivity.class);
			detailIntent.putExtra(OpcionDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	@Override
	public void onBackPressed() {
		if (NAVEGACION == 2) {
			NAVEGACION = 1;
			Modulo.MODULOS_MOSTRADOS_ACTUAL = Modulo.MODULOS;
			Modulo.MODULO_ACTUAL = Constante.PRINCIPAL;
			// Se actualiza el menu izquierdo
			((OpcionListFragment) getSupportFragmentManager().findFragmentById(
					R.id.opcion_list))
					.setListAdapter(new ArrayAdapter<Modulo.ModuloItem>(
							getBaseContext(), R.layout.accordion_list,
							R.id.accordion_list,
							Modulo.MODULOS_MOSTRADOS_ACTUAL));
			// Se muestra contenido vacio en el fragmento derecho
			Bundle arguments = new Bundle();
			OpcionDetailFragment fragment = new OpcionDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.opcion_detail_container, fragment).commit();
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

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

	protected void cerrarSesion() {
		NAVEGACION = 1;
		finish();
	}
}
