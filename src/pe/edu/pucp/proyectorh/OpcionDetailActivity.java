package pe.edu.pucp.proyectorh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 * Actividad que solo se utiliza cuando no se usa la plantilla de dos paneles
 * 
 * @author Cesar
 * 
 */
public class OpcionDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opcion_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			arguments.putString(OpcionDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(OpcionDetailFragment.ARG_ITEM_ID));
			OpcionDetailFragment fragment = new OpcionDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.opcion_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this,
					OpcionListActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
