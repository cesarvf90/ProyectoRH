package pe.edu.pucp.proyectorh;

import pe.edu.pucp.proyectorh.model.Modulo;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
			Bundle arguments = new Bundle();
			arguments.putString(OpcionDetailFragment.ARG_ITEM_ID, id);
			OpcionDetailFragment fragment = new OpcionDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.opcion_detail_container, fragment).commit();

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
			((OpcionListFragment) getSupportFragmentManager().findFragmentById(
					R.id.opcion_list))
					.setListAdapter(new ArrayAdapter<Modulo.ModuloItem>(
							getBaseContext(), R.layout.accordion_list,
							R.id.accordion_list,
							Modulo.MODULOS_MOSTRADOS_ACTUAL));
		} else if (NAVEGACION == 1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Cerrar sesión");
			builder.setMessage("¿Está seguro que desea salir de la aplicación?");
			builder.setPositiveButton("OK", null);
			builder.create();
			builder.show();
		}
	}
}
