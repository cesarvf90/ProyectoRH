package pe.edu.pucp.proyectorh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Actividad principal
 * 
 * @author Cesar
 * 
 */
public class OpcionListActivity extends FragmentActivity implements
		OpcionListFragment.Callbacks {

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
}
