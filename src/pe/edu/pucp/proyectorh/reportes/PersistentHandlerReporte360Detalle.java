package pe.edu.pucp.proyectorh.reportes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.edu.pucp.proyectorh.reportes.Reporte360Detalle.RProcesosEvaluacion;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class PersistentHandlerReporte360Detalle {

	private static String TAG = PersistentHandler.class.toString();

	public static void agregarArchivoPersistente(String texto,
			Context whereIAm, String fileName) {
		String state = Environment.getExternalStorageState();

		try {
			File file = new File(whereIAm.getFilesDir(), fileName);
			OutputStream os = new FileOutputStream(file);
			Writer writer = new OutputStreamWriter(os);
			writer.write(texto);
			writer.close();
			os.close();
			
		} catch (IOException e) {
			System.out.println("Error writing in " + fileName);
		}

		return;
		

	}

	
	public static boolean buscarArchivo(Context whereIAm,
			String fileNameExpected) {

		// busca en memoria interna del dispositivo

		String fileNameReal;
		File filesPath = null;

		try {
			filesPath = new File(whereIAm.getFilesDir().getAbsolutePath());
		} catch (Exception e) {
			System.out.println("No se logró cargar el path de archivos interno, " + e);
			return false;
		}

		for (File f : filesPath.listFiles()) {
			if (f.isFile()) {
				fileNameReal = f.getName();
				if (fileNameExpected.equals(fileNameReal)) {
					return true;
					//una vez encontrado se valida que este actualizado
					
				}
			}
		}
		return false;
		
	}

	public static ArrayList<RProcesosEvaluacion> getProcFromFile(Context whereIAm, String fileName) {
		
		ArrayList<RProcesosEvaluacion> procesos;
		String state = Environment.getExternalStorageState();

		File file = new File(whereIAm.getFilesDir(), fileName);
		try {
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);

		    String s;
		    s = br.readLine(); //fecha
		    s = br.readLine(); //trama supergigante
		    isr.close();
		    is.close();
		    
		    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
		    procesos = gson.fromJson(s,
					new TypeToken<List<RProcesosEvaluacion>>(){}.getType());
		    return procesos;
		    
		} catch (Exception e) {
			System.out.println("Error al leer archivo  " + e);
			
		}
		return null;
		
		
		
	}
}
