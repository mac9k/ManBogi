package com.example.manbogi;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class FavoriteAdapter extends ArrayAdapter<ToonArray>{

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;
	
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
	
	private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();
	 
	
    DisplayImageOptions options = new DisplayImageOptions.Builder()
    .showImageOnLoading(R.drawable.background)
    .showImageForEmptyUri(R.drawable.background)
    .showImageOnFail(R.drawable.background)
    .cacheInMemory(true)
    .cacheOnDisc(true)
    .considerExifParams(true)
    .displayer(new RoundedBitmapDisplayer(20))
    .build();
	
	public FavoriteAdapter(Context context, int layoutResource, ArrayList<ToonArray> objects, boolean openBox){
		super(context, layoutResource, objects);
		this.mContext = context;
		this.mResource = layoutResource;
		this.mList = objects;
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.viewArray = new SparseArray<WeakReference<View>>(objects.size());
		this.helper = new MySQLiteOpenHelper(context);
		this.openBox = openBox;
	}
	
	public void setChecked(int position) {
		isChecked[position] = !isChecked[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder=null;
		int type = getItemViewType(position);
 
		if (convertView == null) {
			convertView = mInflater.inflate(mResource, null);
			holder = new ViewHolder();

			switch (type) {
			case TYPE_ITEM:
				//convertView = mInflater.inflate(R.layout.menuitems_adapter,null);
				//convertView = mInflater.inflate(R.layout.favoritelist,parent, false);
				holder.image = (ImageView) convertView.findViewById(R.id.wishimage);
				holder.title = (TextView) convertView.findViewById(R.id.wishtitle);
				holder.box = (CheckBox)convertView.findViewById( R.id.wishbox);
				
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
		                    //helper.addContact(mList.get(getPosition));
		                    helper.deleteContact(mList.get(getPosition));
				        }
				        else{
				        	int getPosition = (Integer) buttonView.getTag();
				        	mList.get(getPosition).setWish(buttonView.isChecked());
				        	//Log.d("wish", "name :"+mList.get(getPosition).getName()+", wish : " +mList.get(getPosition).getWish());
				        }
				    }
				});
				break;

			case TYPE_SEPARATOR:
				convertView = mInflater.inflate(R.layout.header, null);
				//convertView = LayoutInflater.from(mContext).inflate(R.layout.header,  parent, false);
				holder.header = (TextView) convertView.findViewById(R.id.section_tv);
				break;
			}
			/*
			holder.image = (ImageView) convertView.findViewById(R.id.wishimage);
			holder.title = (TextView) convertView.findViewById(R.id.wishtitle);
			holder.box = (CheckBox)convertView.findViewById( R.id.wishbox);
			
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
	                    //helper.addContact(mList.get(getPosition));
	                    helper.deleteContact(mList.get(getPosition));
			        }
			        else{
			        	int getPosition = (Integer) buttonView.getTag();
			        	mList.get(getPosition).setWish(buttonView.isChecked());
			        	//Log.d("wish", "name :"+mList.get(getPosition).getName()+", wish : " +mList.get(getPosition).getWish());
			        }
			    }
			});*/
			
			
            convertView.setTag(R.id.wishbox, holder.box);
            //convertView.setTag(R.id.section_tv, holder.header);
            convertView.setTag(holder);
		}
		
		if(type == TYPE_ITEM){
		holder = (ViewHolder) convertView.getTag();
		holder.box.setTag(position); 
        holder.box.setChecked(mList.get(position).getWish());
		holder.title.setText(getItem(position).getName());
	
		FavoriteActivity.imageLoader.displayImage(getItem(position).getImage(), holder.image, options);
		}
		else if(type == TYPE_SEPARATOR){
			Log.d("header","header");
			holder = (ViewHolder) convertView.getTag();
			holder.header.setText(mList.get(position).getName());
		}
		
		return convertView;
	}
	
	// ////////////////////////////////////////////////////////////////

	public void addSeparatorItem(final int item) {
		ToonArray temp = new ToonArray();
		switch(item){
		case 1:mSeparatorsSet.clear();temp.setName("MON");break;
		case 2:temp.setName("TUE");break;
		case 3:temp.setName("WED");break;
		case 4:temp.setName("THU");break;
		case 5:temp.setName("FRI");break;
		case 6:temp.setName("SAT");break;
		case 7:temp.setName("SUN");break;
		default : temp.setName("etc");
		}
		mList.add(temp);
		mSeparatorsSet.add(mList.size() - 1);
		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

/*	@Override
	public String getItem(int position) {
		return mList.get(position);
	}*/

	@Override
	public long getItemId(int position) {
		return position;
	}

    //////////////////////////////////////////////////////////////////////////////////////////
    
	static class ViewHolder {
		ImageView image;
        TextView title;
        CheckBox box;
        TextView header;
	}
	
}
