package pe.edu.pucp.proyectorh.services;

import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import android.os.AsyncTask;

public abstract class AsyncCall extends AsyncTask<String, Integer, String> {
	@Override
	protected String doInBackground(String... urls) {
		return ConnectionManager.downloadUrl((String) urls[0]);
	}
}