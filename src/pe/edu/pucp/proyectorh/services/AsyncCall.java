package pe.edu.pucp.proyectorh.services;

import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public abstract class AsyncCall extends AsyncTask<String, Integer, String> {

	private ProgressDialog progressDialog = null;

	public AsyncCall(Activity activity) {
		super();
		progressDialog = new ProgressDialog(activity);
	}

	public AsyncCall() {
	}

	@Override
	protected void onPreExecute() {
		if (progressDialog != null) {
			progressDialog.setMessage("Cargando...");
			progressDialog.show();
		}
	}

	@Override
	protected String doInBackground(String... urls) {
		return ConnectionManager.downloadUrl(urls[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		if (progressDialog != null) {
			// TODO cvasquez: validar que la data ya haya sido recogida
			progressDialog.dismiss();
		}
	}

}