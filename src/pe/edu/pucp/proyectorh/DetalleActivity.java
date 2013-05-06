package pe.edu.pucp.proyectorh;

import pe.edu.pucp.proyectorh.model.Modulo;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCPrincipal;
import pe.edu.pucp.proyectorh.utils.Constante;
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
public class DetalleActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opcion_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			
			if ((Modulo.MODULO_ACTUAL == Constante.REPORTES)
					&& ("4".equals(getIntent()
							.getStringExtra(DetalleFragment.ARG_ITEM_ID)))) {
				ReporteObjetivosBSCPrincipal fragment = new ReporteObjetivosBSCPrincipal();
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.opcion_detail_container, fragment)
						.commit();
			}
			else{
			
			Bundle arguments = new Bundle();
			arguments.putString(DetalleFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(DetalleFragment.ARG_ITEM_ID));
			DetalleFragment fragment = new DetalleFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.opcion_detail_container, fragment).commit();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this,
					MainActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
