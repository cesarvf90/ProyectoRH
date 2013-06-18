package pe.edu.pucp.proyectorh.connection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import pe.edu.pucp.proyectorh.model.Colaborador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseContactosConnection extends SQLiteOpenHelper {

	private static String DB_NAME = "rhcontactos.s3db";
	private static String DB_PATH = "data/data/pe.edu.pucp.proyectorh/databases/";
	private SQLiteDatabase dataBase;
	private final Context myContext;

	public DataBaseContactosConnection(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	/**
	 * Crea una base de datos vacia en el sistema y la sobreescribe con la que
	 * se ha puesto en Assets
	 * 
	 * @throws IOException
	 */
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();

		if (dbExist) {
			// Si ya existe no se hace nada
		} else {
			this.getWritableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error al copiar la Base de Datos");
			}
		}
	}

	/**
	 * Comprueba si la base de datos existe
	 * 
	 * @return
	 */
	private boolean checkDataBase() {
		// SQLiteDatabase checkDB = null;
		// try {
		// String myPath = DB_PATH + DB_NAME;
		// checkDB = SQLiteDatabase.openDatabase(myPath, null,
		// SQLiteDatabase.OPEN_READWRITE);
		// } catch (SQLiteException e) {
		// throw new Error("Error al comprobar si la Base de Datos existe");
		// }
		//
		// if (checkDB != null) {
		// checkDB.close();
		// }
		//
		// return checkDB != null ? true : false;
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}

	/**
	 * Copia la base de datos desde la carpeta Assets sobre la base de datos
	 * vacia recien creada en la carpeta del sistema desde donde es accesible
	 * 
	 * @throws IOException
	 */
	private void copyDataBase() throws IOException {
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);

		// Transfiere los bytes
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	/**
	 * Abre la base de datos
	 * 
	 * @throws SQLiteException
	 */
	public void openDataBase() throws SQLiteException {
		String myPath = DB_PATH + DB_NAME;
		dataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	/**
	 * Cierra la base de datos
	 */
	@Override
	public synchronized void close() {
		if (dataBase != null) {
			dataBase.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * A continuación se crearan los mtétodos de lectura, insercion,
	 * actualizacion y borrado de la base de datos.
	 */

	/**
	 * Obtiene los contactos que ya se encuentran en la base de datos interna
	 */
	public ArrayList<Colaborador> obtenerContactos() {
		ArrayList<Colaborador> contactos = new ArrayList<Colaborador>();

		Cursor c = dataBase.query("CONTACTO", new String[] { "Nombres",
				"Apellidos", "Area", "Puesto", "FechaNacimiento",
				"FechaIngreso", "Correo", "Telefono" }, null, null, null, null,
				null);

		c.moveToFirst();
		while (!c.isAfterLast()) {
			Colaborador contacto = new Colaborador();
			contacto.setNombres(c.getString(0));
			contacto.setApellidos(c.getString(1));
			contacto.setArea(c.getString(2));
			contacto.setPuesto(c.getString(3));
			contacto.setFechaNacimiento(c.getString(4));
			contacto.setFechaIngreso(c.getString(5));
			contacto.setCorreoElectronico(c.getString(6));
			contacto.setTelefono(c.getString(7));
			contactos.add(contacto);
			c.moveToNext();
		}

		c.close();
		return contactos;
	}

	public void insertarContactos(ArrayList<Colaborador> contactos) {
		for (Colaborador contacto : contactos) {
			ContentValues valuesToInsert = new ContentValues();
			valuesToInsert.put("Nombres", contacto.getNombres());
			valuesToInsert.put("Apellidos", contacto.getApellidos());
			valuesToInsert.put("Area", contacto.getArea());
			valuesToInsert.put("Puesto", contacto.getPuesto());
			valuesToInsert
					.put("FechaNacimiento", contacto.getFechaNacimiento());
			valuesToInsert.put("FechaIngreso", contacto.getFechaIngreso());
			valuesToInsert.put("Correo", contacto.getCorreoElectronico());
			valuesToInsert.put("Telefono", contacto.getTelefono());
			dataBase.insert("CONTACTO", null, valuesToInsert);
		}
	}

	public static String obtenerNombre() {
		return DB_NAME;
	}

}
