package com.example.manbogi;



import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
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


enum DayWeek{
	Sunday(0),Monday(1),Tuesday(2),Wednesday(3),Thursday(4),Friday(5),Saturday(6);
	private final int value;
	private DayWeek(int value){this.value=value;}
	public int value(){return value;}
}
enum WebSite{
	Naver(0),Daum(1),Nate(2),Olleh(3),Kakao(4),Tstore(5),Money(6);
	private final int value; 
	private WebSite(int value){this.value=value;}
	public int value(){return value;}
}
enum AppState{
	NotInit(0),Init(1),Err(2);
	private final int value;
	private AppState(int value){this.value=value;}
	public int value(){return value;}
}
 
public class MainActivity extends Activity{
	

	private ActionBarDrawerToggle dtToggle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItem;
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private NavDrawerListAdapter adapter;

	private DayWeek today = DayWeek.Sunday;
	private WebSite site = WebSite.Naver;

	private ImageButton naverBtn,daumBtn,nateBtn;
	private ImageButton monBtn,tueBtn,wedBtn,thuBtn,friBtn,satBtn,sunBtn;
	private boolean webBtn=false,dayBtn=false;
	
	private ListView mLvToons;
	private MyAdapter mAdapter;
	
	private ArrayList<ToonArray> mToonList;
	private boolean openBox=false;
	//public MyProgressDialog mDialog;

	//private  String  serverHostName ="192.168.0.23";
	private  String  serverHostName ="54.64.33.117";//My AWS Server IP
	private static int serverPort = 8989;
	
	
	private ToonArray[] fromServer;
	public static ImageLoader imageLoader = ImageLoader.getInstance();

	private MySQLiteOpenHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initImageLoader(getApplicationContext());
		helper = new MySQLiteOpenHelper(MainActivity.this);
		
		// getting items of slider from array & getting icons
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
		
        
    	naverBtn= (ImageButton)findViewById(R.id.naver);
		daumBtn= (ImageButton)findViewById(R.id.daum);
		nateBtn= (ImageButton)findViewById(R.id.nate);
		
		monBtn = (ImageButton)findViewById(R.id.mon);
		tueBtn = (ImageButton)findViewById(R.id.tue);
		wedBtn = (ImageButton)findViewById(R.id.wed);
		thuBtn = (ImageButton)findViewById(R.id.thu);
		friBtn = (ImageButton)findViewById(R.id.fri);
		satBtn = (ImageButton)findViewById(R.id.sat);
		sunBtn = (ImageButton)findViewById(R.id.sun);
		
		naverBtn.setOnClickListener(OnClicklistener);
		daumBtn.setOnClickListener(OnClicklistener);
		nateBtn.setOnClickListener(OnClicklistener);
		
		monBtn.setOnClickListener(OnClicklistener);
		tueBtn.setOnClickListener(OnClicklistener);
		wedBtn.setOnClickListener(OnClicklistener);
		thuBtn.setOnClickListener(OnClicklistener);
		friBtn.setOnClickListener(OnClicklistener);
		satBtn.setOnClickListener(OnClicklistener);
		sunBtn.setOnClickListener(OnClicklistener);
		
		setMyList();
		
	}
	
	public void setMyList(){ //webToon list 
		mLvToons = (ListView)findViewById(R.id.list);
		mLvToons.setOnItemClickListener(mItemClickListener);
		mToonList = new ArrayList<ToonArray>();
		mAdapter = new MyAdapter(this, R.layout.mylist, mToonList, openBox);
		mLvToons.setAdapter(mAdapter);
		mLvToons.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    //return super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
        return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    dtToggle.onConfigurationChanged(newConfig);
	}
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    if(dtToggle.onOptionsItemSelected(item)){
	        return true;
	    }
	    
		switch (item.getItemId()) {
			case R.id.search:
				//openSearch();
				return true;
			case R.id.check: 
				if(openBox == false){
					openBox = true;setMyList();refreshList(fromServer);
					Toast.makeText(MainActivity.this, "Select Mode", Toast.LENGTH_SHORT).show();
				}
				else{
					openBox = false;setMyList();refreshList(fromServer);
					Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
				}
				
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	    //return super.onOptionsItemSelected(item);
	}


	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		
		Intent intent;
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:intent = new Intent(MainActivity.this,MainActivity.class); //home
				startActivity(intent);finish();
				break;
			case 1:intent = new Intent(MainActivity.this,FavoriteActivity.class); //favorite
				startActivity(intent);finish();
				break;
			case 2:intent = new Intent(MainActivity.this,MainActivity.class); //Setting
				startActivity(intent);finish();
				break;
			case 3:
				break;
			case 4:
				break;
			// TODO Auto-generated method stub

			}
			//dlDrawer.closeDrawer(lvNavList); 
			//dlDrawer.closeDrawer(mDrawerList);
			
		}
	}
	
	private View.OnClickListener OnClicklistener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {/////////////////////////////////����ٰ� ���� button �̹��� �̸� 
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.sun:
				resetWeekButton();resetWebButton();sunBtn.setBackgroundResource(R.drawable.sun_click);
				today = DayWeek.Sunday;dayBtn = true;break;
			case R.id.mon:
				resetWeekButton();resetWebButton();monBtn.setBackgroundResource(R.drawable.mon_click);
				today = DayWeek.Monday;dayBtn = true;break;
			case R.id.tue:
				resetWeekButton();resetWebButton();tueBtn.setBackgroundResource(R.drawable.tue_click);
				today = DayWeek.Tuesday;dayBtn=true;break;
			case R.id.wed:
				resetWeekButton();resetWebButton();wedBtn.setBackgroundResource(R.drawable.wed_click);
				today = DayWeek.Wednesday;dayBtn=true;break;
			case R.id.thu:
				resetWeekButton();resetWebButton();thuBtn.setBackgroundResource(R.drawable.thu_click);
				today = DayWeek.Thursday;dayBtn=true;break;
			case R.id.fri:
				resetWeekButton();resetWebButton();friBtn.setBackgroundResource(R.drawable.fri_click);
				today = DayWeek.Friday;dayBtn=true;break;
			case R.id.sat:
				resetWeekButton();resetWebButton();satBtn.setBackgroundResource(R.drawable.sat_click);
				today = DayWeek.Saturday;dayBtn=true;
				break;

			case R.id.naver:
				resetWebButton();naverBtn.setBackgroundResource(R.drawable._naver);
				site = WebSite.Naver;webBtn=true;break;
							
			case R.id.daum: 
				resetWebButton();daumBtn.setBackgroundResource(R.drawable._daum);
				site=WebSite.Daum;webBtn=true;break;
				
			case R.id.nate: 
				resetWebButton();nateBtn.setBackgroundResource(R.drawable._nate);
				site=WebSite.Nate;webBtn=true;break;
			case R.id.olleh:resetWebButton();site=WebSite.Olleh;break;
			
			
			default:resetWeekButton();resetWebButton(); break;
			}
			
			if(dayBtn==true && webBtn==true){
				/*dialog = MyProgressDialog.show(SubActivity.this,"","",true,true,null);*/
				new DownloadFromServer().execute();
				//dialog.dismiss();
			}
		}
	};

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long l_position) {
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(fromServer[position].fullUrl));
			startActivity(intent);
		}
	};
	
	//////////////////////////////////////////////////////////////////change button image
	private void resetWeekButton(){
		monBtn.setBackgroundResource(R.drawable.mon);
		tueBtn.setBackgroundResource(R.drawable.tue);
		wedBtn.setBackgroundResource(R.drawable.wed);
		thuBtn.setBackgroundResource(R.drawable.thu);
		friBtn.setBackgroundResource(R.drawable.fri);
		satBtn.setBackgroundResource(R.drawable.sat);
		sunBtn.setBackgroundResource(R.drawable.sun);
		dayBtn=false;
	}
	 
	private void resetWebButton(){
		naverBtn.setBackgroundResource(R.drawable._naver);
		daumBtn.setBackgroundResource(R.drawable._daum);
		nateBtn.setBackgroundResource(R.drawable._nate);
		
		webBtn=false;
	}
	
public class DownloadFromServer extends AsyncTask<Void, Void, ToonArray[]>{
		
		String toServer;
		ObjectOutputStream output = null;
		ObjectInputStream input=null;
	
		
		protected void onPreExcute(){
			//mDialog = MyProgressDialog.show(SubActivity.this,"","",true,true,null);
		} 
		
		@Override 
		protected ToonArray[] doInBackground(Void... arg0) {
			try{
				Socket socket = new Socket(serverHostName,serverPort);
				output = new ObjectOutputStream(socket.getOutputStream());output.flush();
				toServer = today.value()+","+site.value();
				output.writeObject(toServer);output.flush();
				
				Log.d("value",today.value()+","+site.value());
				output.flush();
				
				input = new ObjectInputStream(socket.getInputStream());
				Class.forName("com.example.manbogi.WebList");
				Object ReceiveObject = input.readObject();
				if(ReceiveObject instanceof ToonArray[])
					fromServer = (ToonArray[]) ReceiveObject;
				//error check
				
				Log.d("fromServer","recevied");
				socket.close();
				/*if (dialog.isShowing()) 
					dialog.dismiss();*/
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return fromServer ;
		}
		protected void onPostExecute(ToonArray[] result){
			refreshList(result);
		}
	} 
	
	
	void refreshList(ToonArray toonArray[]){
		try{
			mToonList.clear();
			for(int i=0;i<toonArray.length;i++){
					mToonList.add(toonArray[i]);
			}
			mAdapter.notifyDataSetChanged();
		}catch(NullPointerException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
		}
	}
	
		
	
	 public static void initImageLoader(Context context) {
	        // This configuration tuning is custom. You can tune every option, you may tune some of them,
	        // or you can create default configuration by
	        //  ImageLoaderConfiguration.createDefault(this);
	        // method.
	        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
	                .threadPriority(Thread.NORM_PRIORITY - 2)
	                .denyCacheImageMultipleSizesInMemory()
	                .discCacheFileNameGenerator(new Md5FileNameGenerator())
	                .tasksProcessingOrder(QueueProcessingType.LIFO)
	                .writeDebugLogs() // Remove for release app
	                .build();
	        // Initialize ImageLoader with configuration.
	        ImageLoader.getInstance().init(config);
	 }
	 
	 
	protected void onResume(){
		super.onResume();
	}
	protected void onPause(){
		super.onPause();
	}
}