package com.example.manbogi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends Activity{
	WebView mWeb;
	public String address;
	public String hostName,hostPath,hostId,hostPassword;
	public int hostPort;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		
		ProgressDialog ringProgressDialog =
				ProgressDialog.show(WebActivity.this,"Wait...","wait",true);
		ringProgressDialog.setCancelable(false);
		
		ringProgressDialog.setView(mWeb);
		
		ringProgressDialog.show();
		
		if(getConnectInfo() == true){
			Log.d("-ing","ring");
			ringProgressDialog.dismiss();
		
		}
	
		
	}
	
	public boolean getConnectInfo(){
		Intent intent = getIntent();
		address=intent.getStringExtra("address");
		

		
		mWeb = (WebView)findViewById(R.id.web);
		mWeb.setWebViewClient(new MyWebClient());
		WebSettings set = mWeb.getSettings();
		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(true);
		
			
		mWeb.loadUrl(address);
		
		return true;
		
		
	}
	class MyWebClient extends WebViewClient{
		public boolean shouldOverrideUrlLoading(WebView view, String url){
			view.loadUrl(url);
			return true;
		}
	}
	
}
