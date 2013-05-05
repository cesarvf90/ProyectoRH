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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	public static final String USUARIO_VALIDO = "1";
	public static final String USUARIO_INVALIDO = "0";

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
				validaUsuario(usuario, contrasena);
			}
		});
	}

	protected void validaUsuario(String usuario, String contrasena) {
		if (!Constante.CADENA_VACIA.equals(usuario)
				&& !Constante.CADENA_VACIA.equals(contrasena)) {
			validaServicioLogin(usuario, contrasena);
		} else {
			// Se muestra mensaje de campos incompletos
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Campos incompletos");
			builder.setMessage("Debe ingresar su usuario y contraseña para poder iniciar sesión");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
		}
	}

	/**
	 * Llama al ws de validacion de usuario
	 * 
	 * @param usuario
	 * @param contrasena
	 */
	private void validaServicioLogin(String usuario, String contrasena) {
		if (ConnectionManager.connect(this)) {
			// construir llamada al servicio
			String request = Servicio.LoginService + "?username=" + usuario
					+ "&password=" + contrasena;
			new LoginUsuario().execute(request);
		} else {
			// Se muestra mensaje de error de conexion con el servicio
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Error de conexción");
			builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
		}
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
			// Log.i(LoginUsuario.class.getName(),
			// "Recibido: " + result.toString());
			System.out.println("Recibido: " + result.toString());

			final Gson gson = new Gson();
			final RespuestaLogin respuestaLogin = gson.fromJson(result,
					RespuestaLogin.class);
			procesaLogin(respuestaLogin.getRespuesta());
		}
	}

	public class RespuestaLogin {
		private String respuesta;

		public RespuestaLogin() {
		}

		public String getRespuesta() {
			return respuesta;
		}

		public void setRespuesta(String respuesta) {
			this.respuesta = respuesta;
		}

	}

	public void procesaLogin(String respuestaServidor) {
		if (USUARIO_VALIDO.equals(respuestaServidor)) {
			Intent loginIntent = new Intent(getApplicationContext(),
					OpcionListActivity.class);
			startActivity(loginIntent);
		} else if (USUARIO_INVALIDO.equals(respuestaServidor)) {
			// Se muestra mensaje de usuario invalido
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Login inválido");
			builder.setMessage("Combinación de usuario y/o contraseña incorrectos.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
		}
	}
}
