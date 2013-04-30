package pe.edu.pucp.proyectorh.reportes;

import pe.edu.pucp.proyectorh.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ObjetivoAdapter extends BaseAdapter {
	
	private Context context;
	private final String[] objetivos;
 
	public ObjetivoAdapter(Context context, String[] objetivos) {
		this.context = context;
		this.objetivos = objetivos;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
 
			gridView = new View(context);
 
			// get layout from mobile.xml
			//AQUI SE COLOCA EL LAYOUT CON COLOR CORRESPONDIENTE
			gridView = inflater.inflate(R.layout.reportebscobjetivo, null);
 
			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.reportebscObjetivolabel);
			textView.setText(objetivos[position]);
 
			//String mobile = mobileValues[position];
			/*
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			int height = display.getHeight();  // deprecated
			
			gridView.setMinimumHeight(height/2);
			*/
			
			//String objetivo = objetivos[position];
			/*
			if (position == 0) {
				gridView.setBackgroundColor(Color.GREEN);
			} else if (position == 1) {
				gridView.setBackgroundColor(Color.YELLOW);
			} else if (position == 2) {
				gridView.setBackgroundColor(Color.BLUE);
			} else {
				gridView.setBackgroundColor(Color.RED);
			}*/
			
			switch (position){
			case 0 : gridView.setBackgroundColor(Color.parseColor("#00FF00"));break;
			case 1 : gridView.setBackgroundColor(Color.parseColor("#40FF00"));break;
			case 2 : gridView.setBackgroundColor(Color.parseColor("#80FF00"));break;
			case 3 : gridView.setBackgroundColor(Color.parseColor("#BFFF00"));break;
			case 4 : gridView.setBackgroundColor(Color.parseColor("#FFFF00"));break;
			case 5 : gridView.setBackgroundColor(Color.parseColor("#FFBF00"));break;
			case 6 : gridView.setBackgroundColor(Color.parseColor("#FF8000"));break;
			case 7 : gridView.setBackgroundColor(Color.parseColor("#FF4000"));break;
			case 8 : gridView.setBackgroundColor(Color.parseColor("#FF0000"));break;
			case 9 : gridView.setBackgroundColor(Color.parseColor("#FF0000"));break;
	
			}
			
			

 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		return objetivos.length;
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return position;
	}

}
