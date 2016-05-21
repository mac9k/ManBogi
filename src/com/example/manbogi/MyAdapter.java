package com.example.manbogi;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;




public class MyAdapter extends ArrayAdapter<ToonArray>{
	
	
	private Context mContext;
	private int mResource;
	private ArrayList<ToonArray> mList;
	private LayoutInflater mInflater;
	private SparseArray<WeakReference<View>> viewArray;
	private boolean[] isChecked;
	private boolean openBox;

	private SQLiteDatabase db;
	private MySQLiteOpenHelper helper;
	private ContentValues cv;
	
    DisplayImageOptions options = new DisplayImageOptions.Builder()
    .showImageOnLoading(R.drawable.background)
    .showImageForEmptyUri(R.drawable.background)
    .showImageOnFail(R.drawable.background)
    .cacheInMemory(true)
    .cacheOnDisc(true)
    .considerExifParams(true)
    .displayer(new RoundedBitmapDisplayer(20))
    .build();
    
    
	public MyAdapter(Context context, int layoutResource, ArrayList<ToonArray> objects, boolean openBox){
		super(context, layoutResource, objects);
		this.mContext = context;
		this.mResource = layoutResource;
		this.mList = objects;
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.viewArray = new SparseArray<WeakReference<View>>(objects.size());
		this.isChecked = new boolean[viewArray.size()];
		this.helper = new MySQLiteOpenHelper(context);
		this.openBox = openBox;
	}

	public void setChecked(int position) {
		isChecked[position] = !isChecked[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder=null;
 
		if (convertView == null) {
			convertView = mInflater.inflate(mResource, null);
			holder = new ViewHolder();

			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.box = (CheckBox)convertView.findViewById( R.id.box);
			
			if(openBox != false)
				holder.box.setVisibility(convertView.VISIBLE);
			else 
				holder.box.setVisibility(convertView.GONE);
			
			holder.box.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				
			    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
			        if(isChecked){
			        	int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
	                    mList.get(getPosition).setWish(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
	                    //Log.d("wish", "name :"+mList.get(getPosition).getName()+", wish : " +mList.get(getPosition).getWish());
	                   
	                    //helper.DBTest(mList.get(getPosition).getName());
	                    helper.addContact(mList.get(getPosition));
	                   
			        }
			        else{
			        	int getPosition = (Integer) buttonView.getTag();
			        	mList.get(getPosition).setWish(buttonView.isChecked());
			        	//Log.d("wish", "name :"+mList.get(getPosition).getName()+", wish : " +mList.get(getPosition).getWish());
			        }
			    }
			});
			
			convertView.setTag(holder);
            convertView.setTag(R.id.box, holder.box);
		}
		
		holder = (ViewHolder) convertView.getTag();
		holder.box.setTag(position); 
        holder.box.setChecked(mList.get(position).getWish());
		holder.title.setText(getItem(position).getName());
	
		MainActivity.imageLoader.displayImage(getItem(position).getImage(), holder.image, options);
	
		return convertView;
	}
	
	

	static class ViewHolder {
		ImageView image;
        TextView title;
        CheckBox box;
	}
	
	
}