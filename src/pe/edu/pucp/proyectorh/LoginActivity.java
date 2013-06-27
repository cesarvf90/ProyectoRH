package pe.edu.pucp.proyectorh;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.Usuario;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.Constante;
import pe.edu.pucp.proyectorh.utils.EstiloApp;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	public static final String USUARIO_VALIDO = "1";
	public static final String USUARIO_INVALIDO = "0";

	public static Usuario usuario;
	public int DEBUG_NO_LOGIN = 0; // 1: para no validar login

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

				if (DEBUG_NO_LOGIN == 1) {
					Intent loginIntent = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(loginIntent);
				} else {
					validaUsuario(usuario, contrasena);
				}

			}
		});
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
		bar.setTitle("RH++");

		customizarEstilos(this,
				getWindow().getDecorView().findViewById(android.R.id.content));
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
			new LoginUsuario(this).execute(request);
		} else {
			// Se muestra mensaje de error de conexion con el servicio
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Error de conexión");
			builder.setMessage(ConstanteServicio.MENSAJE_PROBLEMA_CONEXION);
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

		public LoginUsuario(Activity activity) {
			super(activity);
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			try {
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (procesaRespuesta(respuesta)) {
					JSONObject datosObject = (JSONObject) jsonObject
							.get("data");
					JSONObject usuarioObject = (JSONObject) datosObject
							.get("usuario");
					usuario = new Usuario(usuarioObject.getString("ID"),
							usuarioObject.getString("Username"),
							usuarioObject.getString("Password"));
					Intent loginIntent = new Intent(LoginActivity.this,
							pe.edu.pucp.proyectorh.MainActivity.class);
					ocultarMensajeProgreso();
					startActivity(loginIntent);
				} else {
					ocultarMensajeProgreso();
				}
			} catch (JSONException e) {
				ocultarMensajeProgreso();
				mostrarErrorComunicacion(e.toString());
			} catch (NullPointerException ex) {
				ocultarMensajeProgreso();
				mostrarErrorComunicacion(ex.toString());
			}
		}
	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (ConstanteServicio.SERVICIO_OK.equals(respuestaServidor)) {
			return true;
		} else if (ConstanteServicio.SERVICIO_ERROR.equals(respuestaServidor)) {
			// Se muestra mensaje de usuario invalido
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Login inválido");
			builder.setMessage("Combinación de usuario y/o contraseña incorrectos.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return false;
		} else {
			// Se muestra mensaje de usuario invalido
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Problema en el servidor");
			builder.setMessage("Hay un problema en el servidor.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return false;
		}
	}

	public static Usuario getUsuario() {
		return usuario;
	}

	public static void setUsuario(Usuario usuario) {
		LoginActivity.usuario = usuario;
	}

	private void mostrarErrorComunicacion(String excepcion) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Error de servicio");
		builder.setMessage(ConstanteServicio.MENSAJE_SERVICIO_NO_DISPONIBLE
				+ excepcion.toString());
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();
	}

}
