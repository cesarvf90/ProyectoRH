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
			
			TextView textAvance = (TextView) gridView
					.findViewById(R.id.reportebscPerspectivaAvance);
			int avance = (position*20);
			textAvance.setText(avance + "%");
 
			setColor(avance, gridView);

 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
	
	public Drawable getBackg(int startColor, int endColor){
		GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, 
				new int[] {startColor, endColor });
	    gd.setStroke(1, Color.parseColor("#000000"));
	    return gd;
	}
	
public void setColor(int avance, View gridView){
		
		if (avance < 10){
			gridView.setBackgroundDrawable(getBackg(0xFFce2029,0xFF9e030b));
		}
		else if (avance < 20){
			gridView.setBackgroundDrawable(getBackg(0xFFff4e00,0xFFc72b00));
		}
		else if (avance < 30){
			gridView.setBackgroundDrawable(getBackg(0xFFff7518,0xFFaa3e00));
		}
		else if (avance < 40){
			gridView.setBackgroundDrawable(getBackg(0xFFffb300,0xFFed7710));
		}
		else if (avance < 50){
			gridView.setBackgroundDrawable(getBackg(0xFFffd900,0xFFffa400));
		}
		else if (avance < 60){
			gridView.setBackgroundDrawable(getBackg(0xFFfff300,0xFFffd400));
		}
		else if (avance < 70){
			gridView.setBackgroundDrawable(getBackg(0xFFd4de1b,0xFFb5bf07));
		}
		else if (avance < 80){
			gridView.setBackgroundDrawable(getBackg(0xFF9cc925,0xFF7ba60d));
		}
		else if (avance < 90){
			gridView.setBackgroundDrawable(getBackg(0xFF5ba825,0xFF377d00));
		}
		else{
			gridView.setBackgroundDrawable(getBackg(0xFF49b700,0xFF378900));
		}
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
