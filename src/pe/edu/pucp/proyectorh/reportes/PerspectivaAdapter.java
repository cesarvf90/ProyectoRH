package pe.edu.pucp.proyectorh.reportes;

import pe.edu.pucp.proyectorh.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerspectivaAdapter extends BaseAdapter {
	
	private Context context;
	private final String[] arrPerspectivas;
 
	public PerspectivaAdapter(Context context, String[] mobileValues) {
		this.context = context;
		this.arrPerspectivas = mobileValues;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
 
			gridView = new View(context);
 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.reportebscperspectiva, null);
 
			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.reportebscPerspectivalabel);
			textView.setText(arrPerspectivas[position]);
 
			//String mobile = mobileValues[position];
			/*
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			int height = display.getHeight();  // deprecated
			
			gridView.setMinimumHeight(height/2);
			*/
			
			String mobile = arrPerspectivas[position];
			
			if (mobile.equals("Financiera")) {
				gridView.setBackgroundColor(Color.BLUE);
			} else if (mobile.equals("Clientes")) {
				gridView.setBackgroundColor(Color.RED);
			} else if (mobile.equals("Interno")) {
				gridView.setBackgroundColor(Color.YELLOW);
			} else {
				gridView.setBackgroundColor(Color.GREEN);
			}
			
			

 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		return arrPerspectivas.length;
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}

}
