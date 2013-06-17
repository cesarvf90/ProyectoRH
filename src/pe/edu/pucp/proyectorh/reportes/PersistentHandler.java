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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.os.Environment;

public class PersistentHandler {

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
					
					//una vez encontrado se valida que este actualizado
					try {
						InputStream is = new FileInputStream(f);
						InputStreamReader isr = new InputStreamReader(is);
					    BufferedReader br = new BufferedReader(isr);

					    String s;
					    s = br.readLine();
					    isr.close();
					    is.close();
					    
					    System.out.println("fecha de archivo: " + s);
					    //compara con fecha actual
					    Date fechaArchivo = DateFormat.getDateTimeInstance().parse(s);
					    System.out.println("format ok");
					    if (System.currentTimeMillis() > ( fechaArchivo.getTime() +  24*60*60*1000)) {
					    	System.out.println("desactualizado por 1 dia");
					    	return false;
					    }
					    else{
					    	System.out.println("esta dentro de las 24 horas");
					    	return true;
					    }
					    
					
					 } catch (Exception e) {
						System.out.println("Error al leer archivo  " + e);
					 }
				}
			}
		}
		return false;
		
	}

	public static ArrayList<ObjetivoDTO> getObjFromFile(Context whereIAm, String fileName) {
		
		ArrayList<ObjetivoDTO> objetivos;
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
		    objetivos = gson.fromJson(s,
					new TypeToken<List<ObjetivoDTO>>(){}.getType());
		    return objetivos;
		    
		} catch (IOException e) {
			System.out.println("Error al leer archivo  " + e);
		}
		return null;
		
		
		
	}
}

