package pe.edu.pucp.proyectorh.reportes;

import pe.edu.pucp.proyectorh.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
			case 0 : gridView.setBackgroundDrawable(getBackg(0xFF49b700,0xFF378900));break;
			case 1 : gridView.setBackgroundDrawable(getBackg(0xFF5ba825,0xFF377d00));break;
			case 2 : gridView.setBackgroundDrawable(getBackg(0xFF9cc925,0xFF7ba60d));break;
			case 3 : gridView.setBackgroundDrawable(getBackg(0xFFd4de1b,0xFFb5bf07));break;
			case 4 : gridView.setBackgroundDrawable(getBackg(0xFFfff300,0xFFffd400));break;
			case 5 : gridView.setBackgroundDrawable(getBackg(0xFFffd900,0xFFffa400));break;
			case 6 : gridView.setBackgroundDrawable(getBackg(0xFFffb300,0xFFed7710));break;
			case 7 : gridView.setBackgroundDrawable(getBackg(0xFFff7518,0xFFaa3e00));break;
			case 8 : gridView.setBackgroundDrawable(getBackg(0xFFff4e00,0xFFc72b00));break;
			case 9 : gridView.setBackgroundDrawable(getBackg(0xFFce2029,0xFF9e030b));break;
	
			}
			
			

 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
	
	public Drawable getBackg(int startColor, int endColor){
		GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, 
				new int[] {startColor, endColor });
	    //gd.setCornerRadius(0f);
	    gd.setStroke(1, Color.parseColor("#000000"));
	    //gd.setCornerRadius(3f);
	    return gd;
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
