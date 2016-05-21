package com.example.manbogi;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class FavoriteActivity extends Activity {
	
	private static final int NAVER = 1;
	private static final int DAUM = 2;
	private static final int NATE = 3;
	
	private static final int MONDAY    = 1;
	private static final int TUESDAY   = 2;
	private static final int WEDNESDAY = 3;	
	private static final int THURSDAY  = 4;
	private static final int FRIDAY    = 5;
	private static final int SATURDAY  = 6;
	private static final int SUNDAY    = 7;
	
	
	private ActionBarDrawerToggle dtToggle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItem;
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private NavDrawerListAdapter adapter;

	private FavoriteAdapter mAdapter;
	private ArrayList<ToonArray> mToonList;
	private ArrayList<ToonArray> monList,tueList,wedList,thuList,friList,satList,sunList;
	// ////////temp
	
	
	private ImageButton naverBtn,daumBtn,nateBtn;
	private WebSite site;

	private ListView mLvToons;
	private MySQLiteOpenHelper helper;
	private SQLiteDatabase db;

	private List<ToonArray> contactList;
	private List<ToonArray> naverContactList;
	private List<ToonArray> daumContactList;
	private List<ToonArray> nateContactList;
	
	private boolean openBox;
	
	public static ImageLoader imageLoader = ImageLoader.getInstance();

	private static final String TABLE_CONTACTS = "wishList";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite);

		helper = new MySQLiteOpenHelper(FavoriteActivity.this);
		initImageLoader(getApplicationContext());

		setMyList();

		contactList = new ArrayList<ToonArray>();
		naverContactList = new ArrayList<ToonArray>();
		daumContactList = new ArrayList<ToonArray>();
		nateContactList = new ArrayList<ToonArray>();

		//getAllContact();
		
		naverBtn= (ImageButton)findViewById(R.id._naver);
		daumBtn= (ImageButton)findViewById(R.id._daum);
		nateBtn= (ImageButton)findViewById(R.id._nate);
		
		naverBtn.setOnClickListener(OnClicklistener);
		daumBtn.setOnClickListener(OnClicklistener);
		nateBtn.setOnClickListener(OnClicklistener);
		
		navMenuTitles=getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons=getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_activity_main_drawer);
        mDrawerList = (ListView) findViewById(R.id.lv_activity_main_nav_list);
		navDrawerItem=new ArrayList<NavDrawerItem>();
		
		navDrawerItem.add(new NavDrawerItem(navMenuTitles[0],navMenuIcons.getResourceId(0,-1)));
		navDrawerItem.add(new NavDrawerItem(navMenuTitles[1],navMenuIcons.getResourceId(1,-1)));
		navDrawerItem.add(new NavDrawerItem(navMenuTitles[2],navMenuIcons.getResourceId(2,-1)));
		navMenuIcons.recycle();
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItem);
        mDrawerList.setAdapter(adapter);
        
        dtToggle=new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
			public void onDrawerClosed(View view) {
				// calling onPrepareOptionsMenu() to show action bar icons
				//super.onDrawerClosed(view);
				invalidateOptionsMenu();Log.d("onDrawerClosed","onDrawerClosed");
			}
			public void onDrawerOpened(View view) {
				// calling onPrepareOptionsMenu() to hide action bar icons
				//super.onDrawerOpened(view);
				invalidateOptionsMenu();Log.d("onDrawerOpened","onDrawerOpened");
			}
		};
		mDrawerLayout.setDrawerListener(dtToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
/*		ToonArray temp = new ToonArray();
		temp.setName("Hi");
		mAdapter.addSeparatorItem(temp);*/
	}
	public void setMyList(){
		mLvToons = (ListView) findViewById(R.id.wishlist);
		mToonList = new ArrayList<ToonArray>();
		monList = new ArrayList<ToonArray>();
		tueList = new ArrayList<ToonArray>();
		wedList = new ArrayList<ToonArray>();
		thuList = new ArrayList<ToonArray>();
		friList = new ArrayList<ToonArray>();
		satList = new ArrayList<ToonArray>();
		sunList = new ArrayList<ToonArray>();
		mAdapter = new FavoriteAdapter(this, R.layout.favoritelist, mToonList, openBox);
		mLvToons.setAdapter(mAdapter);
		mLvToons.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mLvToons.setOnItemClickListener(mItemClickListener);
	}

	
	private View.OnClickListener OnClicklistener = new View.OnClickListener(){
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id._naver:
				site = WebSite.Naver;getAllContact();break;
			case R.id._daum:
				site= WebSite.Daum;getAllContact();break;
			case R.id._nate:
				site=WebSite.Nate;getAllContact();break;
			}
			// TODO Auto-generated method stub
			
		}
	};

	public void callWishList() {

	}

	public void getAllContact() {// List<ToonArray>
		String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				//Add enum variable 
				ToonArray contact = new ToonArray();
				contact.setId(Integer.parseInt(cursor.getString(0)));
				contact.setName(cursor.getString(1));
				contact.setUrl(cursor.getString(2));
				contact.setFullUrl(cursor.getString(3));
				contact.setImage(cursor.getString(4));
				contact.setSite(Integer.parseInt(cursor.getString(5)));
				contact.setWeek(Integer.parseInt(cursor.getString(6)));
				Log.d("db", "name : " + contact.name);

				//중복삭제, 메인액티비티로 이동요망
				if(deleteClone(contact))
					contactList.add(contact);
				
			} while (cursor.moveToNext());
		}
		refreshList(contactList);
	}
	
	public boolean deleteClone(ToonArray contact){
		for (int i = 0; i < contactList.size(); i++) 
			if (contactList.get(i).getName().equals(contact.getName()))
				return false;
		
		return true;
	}

	public void refreshList(List<ToonArray> contactList) {
		try {
			mAdapter.clear();			
			/*temp.setName("월");
			mAdapter.addSeparatorItem(temp);*/
			
			for (int i = 0; i < contactList.size(); i++){
					if(contactList.get(i).getSite() == NAVER  && site == WebSite.Naver){
							seperateWeek(contactList.get(i));
							//seperateList(contactList.get(i));
					}
					else if(contactList.get(i).getSite() == DAUM  && site == WebSite.Daum){
						//mToonList.add(contactList.get(i));
						//seperateList(contactList.get(i));
						seperateWeek(contactList.get(i));
					}
					else if(contactList.get(i).getSite() == NATE  && site == WebSite.Nate){
						//ToonList.add(contactList.get(i));
						//seperateList(contactList.get(i));
						seperateWeek(contactList.get(i));
					}
				}
			seperateList();
			mAdapter.notifyDataSetChanged();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void seperateWeek(ToonArray today){
		Log.d("Week","today  : " + today.getWeek());
			switch(today.getWeek()){
			case 1:monList.add(today);break;
			case 2:tueList.add(today);break;
			case 3:wedList.add(today);break;
			case 4:thuList.add(today);break;
			case 5:friList.add(today);break;
			case 6:satList.add(today);break;
			case 7:sunList.add(today);break;
			}
	}
	
	
	
	public void seperateList(){
			mAdapter.addSeparatorItem(MONDAY);   mToonList.addAll(monList);monList.clear();//monList;mToonList.add(contactList);
			mAdapter.addSeparatorItem(TUESDAY);  mToonList.addAll(tueList);tueList.clear();
			mAdapter.addSeparatorItem(WEDNESDAY);mToonList.addAll(wedList);wedList.clear();
			mAdapter.addSeparatorItem(THURSDAY); mToonList.addAll(thuList);thuList.clear();
			mAdapter.addSeparatorItem(FRIDAY);   mToonList.addAll(friList);friList.clear();
			mAdapter.addSeparatorItem(SATURDAY); mToonList.addAll(satList);satList.clear();
			mAdapter.addSeparatorItem(SUNDAY);   mToonList.addAll(sunList);sunList.clear();
			
			
	}
	
/*	public void seperateList(ToonArray contactList){
		switch(contactList.getWeek()){
		case MONDAY   :mAdapter.addSeparatorItem(MONDAY); mToonList = monList;
					mToonList.add(contactList);break;
		case TUESDAY  :mAdapter.addSeparatorItem(TUESDAY);  mToonList.add(contactList);break;
		case WEDNESDAY:mAdapter.addSeparatorItem(WEDNESDAY);mToonList.add(contactList);break;
		case THURSDAY :mAdapter.addSeparatorItem(THURSDAY); mToonList.add(contactList);break;
		case FRIDAY   :mAdapter.addSeparatorItem(FRIDAY);   mToonList.add(contactList);break;
		case SATURDAY :mAdapter.addSeparatorItem(SATURDAY); mToonList.add(contactList);break;
		case SUNDAY   :mAdapter.addSeparatorItem(SUNDAY);   mToonList.add(contactList);break;
		default:
		}
}*/

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long l_position) {
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(contactList.get(position).getFullUrl()));
			startActivity(intent);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		dtToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (dtToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.check:
			if (openBox == false) {
				openBox = true;setMyList();getAllContact();
				Toast.makeText(FavoriteActivity.this, "Select Mode",Toast.LENGTH_SHORT).show();
			} else {
				openBox = false;Intent intent = new Intent(FavoriteActivity.this, FavoriteActivity.class);
				startActivity(intent);finish();
				Toast.makeText(FavoriteActivity.this, "Back", Toast.LENGTH_SHORT).show();
			}
			// openSearch();
			return true;
		case R.id.search:
			// openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		// return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		Intent intent;

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				intent = new Intent(FavoriteActivity.this, MainActivity.class);
				startActivity(intent);finish();
				break;
			case 1:
				intent = new Intent(FavoriteActivity.this, FavoriteActivity.class);
				startActivity(intent);finish();
				break;
			case 2:
				intent = new Intent(FavoriteActivity.this, MainActivity.class);
				startActivity(intent);finish();
				break;
			case 3:
				break;
			case 4:
				break;
			// TODO Auto-generated method stub

			}
			//dlDrawer.closeDrawer(lvNavList);
		}
	}
}