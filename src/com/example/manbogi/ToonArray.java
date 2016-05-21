package com.example.manbogi;

import java.io.Serializable;

enum ToonState{
	Start(0),NotUpdate(1),Update(2),Resting(3);
	private final int value;
	private ToonState(int value){this.value=value;}
	public int value(){return value;}
}



class ToonArray implements Serializable {
	
	 Integer id;
	 String name;
	 String url;
	 String fullUrl;
	 String image;
	 ToonState state;
	 Boolean wish;
	 Integer site;
	 Integer week;
	
	
	private static final long serialVersionUID = 13333313222228643L;
	
	void setWeek(Integer week){
		this.week = week;
	}
	
	void setSite(Integer site){
		this.site = site;
	}
	
	void setId(Integer id){
		this.id = id;
	}
	
	void setName(String name){
		this.name = name;
	}
	
	void setFullUrl(String fullUrl){
		this.fullUrl = fullUrl;
	}
	
	void setImage(String image){
		this.image = image;
	}
	
	void setUrl(String url){
		this.url=url;
	}
	
	void setState(ToonState state){
		this.state=state;
	}
	
	void setWish(Boolean wish){
		this.wish=wish;
	}
	
	Integer getWeek(){
		return week;
	}
	Integer getSite(){
		return site;
	}
	Integer getId(){
		return id;
	}
	
	String getName(){
		return name;
	}
	
	String getFullUrl(){
		return fullUrl;
	}
	
	String getImage(){
		return image;
	}
	
	String getUrl(){
		return url;
	}
	
	ToonState getState(){
		return state;
	}
	
	Boolean getWish(){
		return wish;
	}
	
	
	ToonArray(){
		this.wish = false;
		
	}
	ToonArray(String url){
		this.url = url;
		this.wish = false;
	}
	ToonArray(String url , String name){
		this.url = url;
		this.name = name;
		this.state = ToonState.Start;
		this.wish = false;
	}
	
}