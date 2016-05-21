package com.example.manbogi;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ToonListActivity extends Activity {
	
	
	private String[] str;
	private ArrayAdapter<String> m_Adapter; // temp variable   
	private ListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylist);
		
		listView = (ListView)findViewById(R.id.list);
		
		Intent intent = getIntent();
		str = (String[])intent.getSerializableExtra("str");
		
		m_Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
	    listView.setAdapter(m_Adapter);
	    //listView.setOnItemClickListener(onClickListItem);
		
	    for(int i=0; i<str.length; i++){
	    	m_Adapter.add(str[i]);
	    }
	}
}
