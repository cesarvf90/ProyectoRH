package pe.edu.pucp.proyectorh.reportes;

import android.content.Context;
import android.database.sqlite.*;

public class Handler_sqlite extends SQLiteOpenHelper {
	
	public static final String TABLA_REPORTE_OBJS = "ReporteObjetivos";
	
	public Handler_sqlite(Context context) {
	    super(context, "RHDataBase.db", null, 1);
	  }
	
	

	
	@Override
	public void onCreate(SQLiteDatabase database) {
		//String createQuery = "CREATE TABLE country (_id integer primary key autoincrement,name, cap, code);";
		
		String query = "CREATE TABLE ReporteObjetivos (_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
						"id, nombre,periodo, peso, avance, asignado, creador, tipo, padre, bscid );";
	    database.execSQL(query);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	    db.execSQL("DROP TABLE IF EXISTS ReporteObjetivos");
	    
	    onCreate(db);
	}
	
	


}
