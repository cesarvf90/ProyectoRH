package pe.edu.pucp.proyectorh;

import com.google.gson.Gson;

import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.Constante;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.getBackground().setColorFilter(0xFF91E2A9,
				PorterDuff.Mode.MULTIPLY);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String usuario = ((EditText) findViewById(R.id.usuarioText))
						.getText().toString();
				String contrasena = ((EditText) findViewById(R.id.contrasenaText))
						.getText().toString();
				if (validaUsuario(usuario, contrasena)) {
					Intent mainIntent = new Intent(v.getContext(),
							OpcionListActivity.class);
					startActivity(mainIntent);
				}
			}
		});
	}

	protected boolean validaUsuario(String usuario, String contrasena) {
		if (!Constante.CADENA_VACIA.equals(usuario)
				&& !Constante.CADENA_VACIA.equals(contrasena)) {
			return validaServicioLogin(usuario, contrasena);
		} else {
			// Se muestra mensaje de campos incompletos
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Campos incompletos");
			builder.setMessage("Debe ingresar su usuario y contraseña para poder iniciar sesión");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return false;
		}
	}

	/**
	 * Llama al ws de validacion de usuario
	 * 
	 * @param usuario
	 * @param contrasena
	 * @return
	 */
	private boolean validaServicioLogin(String usuario, String contrasena) {
		if (ConnectionManager.connect(this)) {
			// fetch data
			new LoginUsuario().execute(Servicio.LoginService);
		} else {
			// Se muestra mensaje de error de conexion con el servicio
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Error de conexción");
			builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return false;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	@Override
	protected void onRestart() {
		EditText usuarioText = (EditText) findViewById(R.id.usuarioText);
		EditText contrasenaText = (EditText) findViewById(R.id.contrasenaText);
		usuarioText.setText(Constante.CADENA_VACIA);
		contrasenaText.setText(Constante.CADENA_VACIA);
		super.onRestart();
	}

	public class LoginUsuario extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			Log.i(LoginUsuario.class.getName(),
					"Recibido: " + result.toString());
			final Gson gson = new Gson();
			final P product = gson.fromJson(result, P.class);
		}
	}

	public class P {
		public String nombre;

		public P() {
		}
	}
}
