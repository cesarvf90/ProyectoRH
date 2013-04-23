package pe.edu.pucp.proyectorh;

import pe.edu.pucp.proyectorh.utils.Constante;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		/* Cambiar color de la vista */
		// View view = findViewById(R.id.linearLayout1);
		// View root = view.getRootView();
		// root.setBackgroundColor(getResources().getColor(
		// android.R.color.holo_green_light));
		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.getBackground().setColorFilter(0xFF91E2A9,
				PorterDuff.Mode.MULTIPLY);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText usuarioText = (EditText) findViewById(R.id.usuarioText);
				EditText contrasenaText = (EditText) findViewById(R.id.contrasenaText);
				if (!Constante.CADENA_VACIA.equals(usuarioText.getText()
						.toString())
						&& !Constante.CADENA_VACIA.equals(contrasenaText
								.getText().toString())) {
					Intent mainIntent = new Intent(v.getContext(),
							OpcionListActivity.class);
					startActivity(mainIntent);
				}
			}
		});
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

}
