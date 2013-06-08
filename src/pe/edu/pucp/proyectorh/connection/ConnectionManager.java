package pe.edu.pucp.proyectorh.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionManager {

	// public static int READ_TIME_OUT = 10000;
	public static int READ_TIME_OUT = 15000;
	public static int CONNECT_TIME_OUT = 15000;
	// public static int LENGTH = 50000;
	public static int LENGTH = 100000;

	public static NetworkInfo getConnection(Activity activity) {
		ConnectivityManager connMgr = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return isNetworkInfoConnected(networkInfo) ? networkInfo : null;
	}

	public static boolean connect(Activity activity) {
		return getConnection(activity) != null;
	}

	public static String downloadUrl(String myurl) {

		InputStream is = null;

		int len = LENGTH;

		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setReadTimeout(READ_TIME_OUT);
//			conn.setConnectTimeout(CONNECT_TIME_OUT);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			conn.connect();
			int response = conn.getResponseCode();
			Log.d("DEBUG", "The response is " + response);
			is = conn.getInputStream();
			// TODO cvasquez: usar http://javarevisited.blogspot.com/2012/08/convert-inputstream-to-string-java-example-tutorial.html
			String contentAsString = readIt(is, len);
			return contentAsString;
		} catch (IOException ex) {
			return "ERROR: No se pudo bajar la pagina web solicitada. La URL puede ser invalida";
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean isError(String result) {
		if (result.startsWith("ERROR"))
			return true;

		try {
			JSONObject obj = new JSONObject(result);
			if (obj.has("ERORR"))
				return true;
		} catch (JSONException e) {
		}
		return false;

	}

	public static boolean isNetworkInfoConnected(NetworkInfo networkInfo) {
		return networkInfo != null && networkInfo.isConnected();
	}

	private static String readIt(InputStream is, int len) throws IOException,
			UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(is);
		char[] buffer = new char[len];
		Arrays.fill(buffer, ' ');
		reader.read(buffer);
		return new String(buffer).trim();
	}
}
