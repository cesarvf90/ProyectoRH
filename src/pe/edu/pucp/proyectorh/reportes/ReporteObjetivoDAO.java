package pe.edu.pucp.proyectorh.reportes;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ReporteObjetivoDAO {
	/*
	 * String query = "CREATE TABLE ReporteObjetivos (_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
						"id, nombre,periodo, peso, avance, asignado, creador, tipo, padre, bscid );";*/
	
	// Database fields
	  private SQLiteDatabase database;
	  private Handler_sqlite dbHelper;

	  public ReporteObjetivoDAO(Context context) {
	    dbHelper = new Handler_sqlite(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  
	  public int createComment(String comment) {
		    ContentValues values = new ContentValues();
		    //values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
		    long insertId = database.insert(Handler_sqlite.TABLA_REPORTE_OBJS, null,
		        values);
		    return (int)insertId;
		  }
	  
	

}
