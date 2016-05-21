package com.example.manbogi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

enum ServerState{
	NotInit(0),Init(1),Update(2),Err(3);
	private final int value;
	private ServerState(int value){this.value=value;}
	public int value(){return value;}
}

class WebList {
	
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
	
	public  ToonArray[] naverSun,naverMon,naverTue,naverWed,naverThu,naverFri,naverSat;
	public  ToonArray[] daumSun,daumMon,daumTue,daumWed,daumThu,daumFri,daumSat;
	public  ToonArray[] nateSun,nateMon,nateTue,nateWed,nateThu,nateFri,nateSat;
	
	public  ToonArray[] tempNaverSun,tempNaverMon,tempNaverTue,tempNaverWed,tempNaverThu,tempNaverFri,tempNaverSat;
	public  ToonArray[] tempDaumSun,tempDaumMon,tempDaumTue,tempDaumWed,tempDaumThu,tempDaumFri,tempDaumSat;
	public  ToonArray[] tempNateSun,tempNateMon,tempNateTue,tempNateWed,tempNateThu,tempNateFri,tempNateSat;
	
	public  String naverMonWeb,naverTueWeb,naverWedWeb,naverThuWeb,naverFriWeb,naverSatWeb,naverSunWeb;
	public  String daumMonWeb,daumTueWeb,daumWedWeb,daumThuWeb,daumFriWeb,daumSatWeb,daumSunWeb;
	
	public  String naver,daum,nate;
	public  ServerState serverState;
	
	public int totalOfToon;
	static private String webUrl;
	static private String tempWebUrl;
	
	public void tempAllVar(){
		tempNaverSun=naverSun;tempNaverMon=naverMon;tempNaverTue=naverTue;
		tempNaverWed=naverWed;tempNaverThu=naverThu;tempNaverFri=naverFri;tempNaverSat=naverSat;
		
		tempDaumSun=daumSun;tempDaumMon=daumMon;tempDaumTue=daumTue;
		tempDaumWed=daumWed;tempDaumThu=daumThu;tempDaumFri=daumFri;tempDaumSat=daumSat;
		
		tempNateSun=nateSun;tempNateMon=nateMon;tempNateTue=nateTue;
		tempNateWed=nateWed;tempNateThu=nateThu;tempNateFri=nateFri;tempNateSat=nateSat;	
	}
	
	public void loopUrl(String strUrl){  //Paste from web site source code
		synchronized(this){
			int lenRead = 0;
			StringBuffer sb = new StringBuffer();
			
			try{
				URL url = new URL(strUrl);
				URLConnection urlConnection =url.openConnection();
	
				BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
		
				while((lenRead = br.read())!= -1)
					sb.append((char)lenRead);
				
			}catch (IOException e){
				e.printStackTrace();
			}	
			
			webUrl = new String(sb.toString());
			tempWebUrl = webUrl;
			}
	}

	public void serverStart(){//App init
		
		
		for (int i = 0; i < 7; i++) {//Initialize list ( count webtoon)
			naverListUpdate(i);
			daumListUpdate(i);
			nateListUpdate(i);
		}
		
		for(int i =0; i<7; i++){//7days in a week, naver
			naverFilledUpURL(i);
			naverFilledUpName(i);
			naverFilledUpImage(i);
			naverFilledUpSite_Day(i);
		}
		
		for(int i =0; i<7; i++){//7days in a week,daum
			daumFilledUpURL(i);
			daumFilledUpName(i);
			daumFilledUpImage(i);
			daumFilledUpSite_Day(i);
		}
		
		for(int i =0; i<7; i++){//7days in a week,nate
			nateFilledUpURL(i);
			nateFilledUpName(i);
			nateFilledUpImage(i);
			nateFilledUpSite_Day(i);
		}
		
	
	}
	
	public void listUpdate(){ //Initialize list ( count webtoon)
	
		for(int i =0; i<7; i++){//7days in a week
			naverListUpdate(i);
			daumListUpdate(i);
			nateListUpdate(i);
		}
	}
	public void naverFilledUpImage(int today){
		naverSubFilled(today);
		String[] split = tempWebUrl.split("전체보기");
		totalOfToon =split.length;
		
		for(int i=0; i<totalOfToon-1; i++){
			int start = tempWebUrl.indexOf("blank.gif");
			int end = tempWebUrl.indexOf("width=\"83\"");
			
			switch(today){
			case 0:naverSun[i].setImage(tempWebUrl.substring(start + 17, end-2));
			break;//Sunday	
			 
			case 1:naverMon[i].setImage(tempWebUrl.substring(start + 17, end-2));
			break;//Monday
			
			case 2:naverTue[i].setImage(tempWebUrl.substring(start + 17, end-2));
			break;//Tuesday
			
			case 3:naverWed[i].setImage(tempWebUrl.substring(start + 17, end-2));
			break;//Wednesday
			
			case 4:naverThu[i].setImage(tempWebUrl.substring(start + 17, end-2));
			break;//Thursday
			
			case 5:naverFri[i].setImage(tempWebUrl.substring(start + 17, end-2));
			break;//Friday
			
			case 6:naverSat[i].setImage(tempWebUrl.substring(start + 17, end-2));
			break;//Saturday
			
			default:
			}
			tempWebUrl = tempWebUrl.substring(end+1);
		}
	}
	
	public void daumFilledUpImage(int today){
		daumSubFilled(today);
		String[] split = tempWebUrl.split("<a href=");
		totalOfToon = split.length;
		
		for(int i=0; i<totalOfToon-1; i++){
			int start = tempWebUrl.indexOf("src=");
			int end = tempWebUrl.indexOf("/>");
			
			switch(today){
			case 0:daumSun[i].setImage(tempWebUrl.substring(start+5,end-2));break;//Sunday
			
			case 1:daumMon[i].setImage(tempWebUrl.substring(start+5,end-2));break;//Monday
			
			case 2:daumTue[i].setImage(tempWebUrl.substring(start+5,end-2));break;//Tuesday
			
			case 3:daumWed[i].setImage(tempWebUrl.substring(start+5,end-2));break;//Wednesday
			
			case 4:daumThu[i].setImage(tempWebUrl.substring(start+5,end-2));break;//Thursday
			
			case 5:daumFri[i].setImage(tempWebUrl.substring(start+5,end-2));break;//Friday
			
			case 6:daumSat[i].setImage(tempWebUrl.substring(start+5,end-2));break;//Saturday
				default:
			}
			tempWebUrl = tempWebUrl.substring(end+1);
		}
	}
	
	public void nateFilledUpImage(int today){
		nateSubFilled(today);
		String[] split = tempWebUrl.split("<a href=");
		totalOfToon = split.length;
		
		for(int i=0; i<totalOfToon-1; i++){
			int start = tempWebUrl.indexOf("img src=");
			int end = tempWebUrl.indexOf("alt=");
			
			switch(today){
			case 0:nateSun[i].setImage(tempWebUrl.substring(start+9,end-2));break;//Sunday
			
			case 1:nateMon[i].setImage(tempWebUrl.substring(start+9,end-2));break;//Monday
			
			case 2:nateTue[i].setImage(tempWebUrl.substring(start+9,end-2));break;//Tuesday
			
			case 3:nateWed[i].setImage(tempWebUrl.substring(start+9,end-2));break;//Wednesday
			
			case 4:nateThu[i].setImage(tempWebUrl.substring(start+9,end-2));break;//Thursday
			
			case 5:nateFri[i].setImage(tempWebUrl.substring(start+9,end-2));break;//Friday
			
			case 6:nateSat[i].setImage(tempWebUrl.substring(start+9,end-2));break;//Saturday
				default:
			}
			tempWebUrl = tempWebUrl.substring(end+1);
		}
	}
	
	public void naverFilledUpURL(int today){
			naverSubFilled(today);
			String[] split = tempWebUrl.split("전체보기");
			String address = "http://m.comic.naver.com/";
			totalOfToon =split.length;
			
			for(int i=0; i<totalOfToon-1; i++){
				int start = tempWebUrl.indexOf("\"more\">");
				int end = tempWebUrl.indexOf("\">전체보기");
				
				switch(today){//Constructor and setter
				case 0:naverSun[i] = new ToonArray(tempWebUrl.substring(start + 16, end));
				naverSun[i].setFullUrl(address+naverSun[i].url);break;//Sunday	
				
				case 1:naverMon[i] = new ToonArray(tempWebUrl.substring(start + 16, end));
				naverMon[i].setFullUrl(address+naverMon[i].url);break;//Monday	
				
				case 2:naverTue[i] = new ToonArray(tempWebUrl.substring(start + 16, end));
				naverTue[i].setFullUrl(address+naverTue[i].url);break;//Tuesday	
				
				case 3:naverWed[i] = new ToonArray(tempWebUrl.substring(start + 16, end));
				naverWed[i].setFullUrl(address+naverWed[i].url);break;//Wednesday
				
				case 4:naverThu[i] = new ToonArray(tempWebUrl.substring(start + 16, end));
				naverThu[i].setFullUrl(address+naverThu[i].url);break;//Thursday
				
				case 5:naverFri[i] = new ToonArray(tempWebUrl.substring(start + 16, end));
				naverFri[i].setFullUrl(address+naverFri[i].url);break;//Friday	
				
				case 6:naverSat[i] = new ToonArray(tempWebUrl.substring(start + 16, end));
				naverSat[i].setFullUrl(address+naverSat[i].url);break;//Saturday
				
				default:break;
				}
				
				tempWebUrl = tempWebUrl.substring(end+1);
				}
		
	}
	
	public void daumFilledUpURL(int today){//filled up URL 
			int start, end;
			daumSubFilled(today);//add 10.30
			
			String[] split = tempWebUrl.split("<a href=");
			String address = "http://m.webtoon.daum.net/m";
			totalOfToon = split.length;
			
			for(int i=0; i<totalOfToon-1;i++){
				start = tempWebUrl.indexOf("<a href=");
				end =   tempWebUrl.indexOf("\" title=");
				switch(today){
				case 0:daumSun[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				daumSun[i].setFullUrl(address+daumSun[i].url);break;//Sunday
				
				case 1:daumMon[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				daumMon[i].setFullUrl(address+daumMon[i].url);break;//Monday
				
				case 2:daumTue[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				daumTue[i].setFullUrl(address+daumTue[i].url);break;//Tuesday
				
				case 3:daumWed[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				daumWed[i].setFullUrl(address+daumWed[i].url);break;//Wednesday
				
				case 4:daumThu[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				daumThu[i].setFullUrl(address+daumThu[i].url);break;//Thursday
				
				case 5:daumFri[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				daumFri[i].setFullUrl(address+daumFri[i].url);break;//Friday
				
				case 6:daumSat[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				daumSat[i].setFullUrl(address+daumSat[i].url);break;//Saturday
				
				default:
				}
				tempWebUrl = tempWebUrl.substring(end+1);
			}
			
		
	}
	
	public void nateFilledUpURL(int today){//filled up URL
			int start,end;
			nateSubFilled(today);
			
			String[] split = tempWebUrl.split("<a href=");
			String address = "http://m.comics.nate.com/main/info?";
			totalOfToon = split.length;
			
			for(int i=0; i<totalOfToon-1;i++){
				start = tempWebUrl.indexOf("<a href=");
				end =   tempWebUrl.indexOf("\" class=\"wtl_toon");
				switch(today){
				case 0:nateSun[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				nateSun[i].setFullUrl(address+nateSun[i].url.substring(18));break;//Sunday
				
				case 1:nateMon[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				nateMon[i].setFullUrl(address+nateMon[i].url.substring(18));break;//Monday
				
				case 2:nateTue[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				nateTue[i].setFullUrl(address+nateTue[i].url.substring(18));break;//Tuesday
				
				case 3:nateWed[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				nateWed[i].setFullUrl(address+nateWed[i].url.substring(18));break;//Wednesday
				
				case 4:nateThu[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				nateThu[i].setFullUrl(address+nateThu[i].url.substring(18));break;//Thursday
				
				case 5:nateFri[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				nateFri[i].setFullUrl(address+nateFri[i].url.substring(18));break;//Friday
				
				case 6:nateSat[i] = new ToonArray(tempWebUrl.substring(start+9,end));
				nateSat[i].setFullUrl(address+nateSat[i].url.substring(18));break;//Saturday
				
				default:
				}
				tempWebUrl = tempWebUrl.substring(end+1);
			}
		
	}
	
	public void naverFilledUpName(int today){// filled up webtoon name
		
				int start,end;
				naverSubFilled(today);
			for(int i=0; i<totalOfToon-1; i++){/////////////�ڵ庸�� ���ÿ� print��
				switch(today){ // ������
				case 0: naverSubFilled(today);naverCutURL();//Sunday
				start= tempWebUrl.indexOf(naverSun[i].url);
				end=tempWebUrl.indexOf("</a></dt>",start);start=tempWebUrl.indexOf("title",end-40);
				end=tempWebUrl.indexOf("\">",start);
				naverSun[i].setName(tempWebUrl.substring(start+7,end));tempWebUrl=tempWebUrl.substring(end);
				break;
				
				case 1:naverSubFilled(today);naverCutURL();//Monday
				start= tempWebUrl.indexOf(naverMon[i].url);
				end=tempWebUrl.indexOf("</a></dt>",start);start=tempWebUrl.indexOf("title",end-40);
				end=tempWebUrl.indexOf("\">",start);
				naverMon[i].setName(tempWebUrl.substring(start+7,end));tempWebUrl=tempWebUrl.substring(end);
				break;
				
				case 2:naverSubFilled(today);naverCutURL();//Tuesday
				start= tempWebUrl.indexOf(naverTue[i].url);
				end=tempWebUrl.indexOf("</a></dt>",start);start=tempWebUrl.indexOf("title",end-40);
				end=tempWebUrl.indexOf("\">",start);
				naverTue[i].setName(tempWebUrl.substring(start+7,end));tempWebUrl=tempWebUrl.substring(end);
				break;
				
				case 3:naverSubFilled(today);naverCutURL();//Wednesday
				start= tempWebUrl.indexOf(naverWed[i].url);
				end=tempWebUrl.indexOf("</a></dt>",start);start=tempWebUrl.indexOf("title",end-40);
				end=tempWebUrl.indexOf("\">",start);
				naverWed[i].setName(tempWebUrl.substring(start+7,end));tempWebUrl=tempWebUrl.substring(end);
				break;
				
				case 4:naverSubFilled(today);naverCutURL();//Thursday
				start= tempWebUrl.indexOf(naverThu[i].url);
				end=tempWebUrl.indexOf("</a></dt>",start);start=tempWebUrl.indexOf("title",end-40);
				end=tempWebUrl.indexOf("\">",start);
				naverThu[i].setName(tempWebUrl.substring(start+7,end));tempWebUrl=tempWebUrl.substring(end);
				break;
				
				case 5:naverSubFilled(today);naverCutURL();//Friday
				start= tempWebUrl.indexOf(naverFri[i].url);
				end=tempWebUrl.indexOf("</a></dt>",start);start=tempWebUrl.indexOf("title",end-40);
				end=tempWebUrl.indexOf("\">",start);
				naverFri[i].setName(tempWebUrl.substring(start+7,end));tempWebUrl=tempWebUrl.substring(end);
				break;
				
				case 6:naverSubFilled(today);naverCutURL();//Saturday
				start= tempWebUrl.indexOf(naverSat[i].url);
				end=tempWebUrl.indexOf("</a></dt>",start);start=tempWebUrl.indexOf("title",end-40);
				end=tempWebUrl.indexOf("\">",start);
				naverSat[i].setName(tempWebUrl.substring(start+7,end));tempWebUrl=tempWebUrl.substring(end);
				break;
				}
			}
		
	}

	
	public void naverCutURL(){// sub name
		int cut;
		cut=tempWebUrl.indexOf("list_area daily_img");
		tempWebUrl=tempWebUrl.substring(cut);
	}
	
	public void daumFilledUpName(int today){//add 10.30 filled up webtoon name
		
			int start,end;
			String target = "<a href=\"";
			String close = "\"";
			for(int i=0; i<totalOfToon-1;i++){
				switch(today){
				case 0:loopUrl(daumSunWeb);start=tempWebUrl.indexOf(target+daumSun[i].url+close);
				end=tempWebUrl.indexOf("class=",start);start=tempWebUrl.indexOf("title=",end-40);
				daumSun[i].setName(tempWebUrl.substring(start+7,end-2));break;//Sunday
				
				case 1:loopUrl(daumMonWeb);start=tempWebUrl.indexOf(target+daumMon[i].url+close);
				end=tempWebUrl.indexOf("class=",start);start=tempWebUrl.indexOf("title=",end-40);
				daumMon[i].setName(tempWebUrl.substring(start+7,end-2));break;//Monday
				
				case 2:loopUrl(daumTueWeb);start=tempWebUrl.indexOf(target+daumTue[i].url+close);
				end=tempWebUrl.indexOf("class=",start);start=tempWebUrl.indexOf("title=",end-40);
				daumTue[i].setName(tempWebUrl.substring(start+7,end-2));break;//Tuesday
				
				case 3:loopUrl(daumWedWeb);start=tempWebUrl.indexOf(target+daumWed[i].url+close);
				end=tempWebUrl.indexOf("class=",start);start=tempWebUrl.indexOf("title=",end-40);
				daumWed[i].setName(tempWebUrl.substring(start+7,end-2));break;//Wednesday
				
				case 4:loopUrl(daumThuWeb);start=tempWebUrl.indexOf(target+daumThu[i].url+close);
				end=tempWebUrl.indexOf("class=",start);start=tempWebUrl.indexOf("title=",end-40);
				daumThu[i].setName(tempWebUrl.substring(start+7,end-2));break;//Thursday
				
				case 5:loopUrl(daumFriWeb);start=tempWebUrl.indexOf(target+daumFri[i].url+close);
				end=tempWebUrl.indexOf("class=",start);start=tempWebUrl.indexOf("title=",end-40);
				daumFri[i].setName(tempWebUrl.substring(start+7,end-2));break;//Friday
				
				case 6:loopUrl(daumSatWeb);start=tempWebUrl.indexOf(target+daumSat[i].url+close);
				end=tempWebUrl.indexOf("class=",start);start=tempWebUrl.indexOf("title=",end-40);
				daumSat[i].setName(tempWebUrl.substring(start+7,end-2));break;//Saturday
				
				default:
				
				}
			}
		
	}
	
	public void nateFilledUpName(int today){
		
			int start,end;
			String target = "http://comics.nate.com/";
			for(int i=0; i<totalOfToon-1;i++){
				switch(today){
				case 0:loopUrl(target+nateSun[i].url);start=tempWebUrl.indexOf("var title");
				end=tempWebUrl.indexOf("\";",start);nateSun[i].setName(tempWebUrl.substring(start+13,end));break;//Sunday
				
				case 1:loopUrl(target+nateMon[i].url);start=tempWebUrl.indexOf("var title");
				end=tempWebUrl.indexOf("\";",start);nateMon[i].setName(tempWebUrl.substring(start+13,end));break;//Monday
				
				case 2:loopUrl(target+nateTue[i].url);start=tempWebUrl.indexOf("var title");
				end=tempWebUrl.indexOf("\";",start);nateTue[i].setName(tempWebUrl.substring(start+13,end));break;//Tuesday
				
				case 3:loopUrl(target+nateWed[i].url);start=tempWebUrl.indexOf("var title");
				end=tempWebUrl.indexOf("\";",start);nateWed[i].setName(tempWebUrl.substring(start+13,end));break;//Wednesday
				
				case 4:loopUrl(target+nateThu[i].url);start=tempWebUrl.indexOf("var title");
				end=tempWebUrl.indexOf("\";",start);nateThu[i].setName(tempWebUrl.substring(start+13,end));break;//Thursday
				
				case 5:loopUrl(target+nateFri[i].url);start=tempWebUrl.indexOf("var title");
				end=tempWebUrl.indexOf("\";",start);nateFri[i].setName(tempWebUrl.substring(start+13,end));break;//Friday
				
				case 6:loopUrl(target+nateSat[i].url);start=tempWebUrl.indexOf("var title");
				end=tempWebUrl.indexOf("\";",start);nateSat[i].setName(tempWebUrl.substring(start+13,end));break;//Saturday
				
				default:
				}
			}
		
	}
	
	public void naverFilledUpUpdate(int today){ //update check
		String address = "http://comic.naver.com/";
		
		
		switch(today){
		case 0:totalOfToon=naverSun.length;break;
		case 1:totalOfToon=naverMon.length;break;
		case 2:totalOfToon=naverTue.length;break;
		case 3:totalOfToon=naverWed.length;break;
		case 4:totalOfToon=naverThu.length;break;
		case 5:totalOfToon=naverFri.length;break;
		case 6:totalOfToon=naverSat.length;break;
		}
		
		for(int i=0;i<totalOfToon-1;i++){	//Check update/rest/not yet 
			switch(today){
			case 0:loopUrl(address+naverSun[i].url);naverContentFile(naverSun[i]);break;//Sunday
			case 1:loopUrl(address+naverMon[i].url);naverContentFile(naverMon[i]);break;//Monday
			case 2:loopUrl(address+naverTue[i].url);naverContentFile(naverTue[i]);break;//Tuesday
			case 3:loopUrl(address+naverWed[i].url);naverContentFile(naverWed[i]);break;//Wednesday
			case 4:loopUrl(address+naverThu[i].url);naverContentFile(naverThu[i]);break;//Thursday
			case 5:loopUrl(address+naverFri[i].url);naverContentFile(naverFri[i]);break;//Friday
			case 6:loopUrl(address+naverSat[i].url);naverContentFile(naverSat[i]);break;//Saturday
			default:break;
			}
		}
	}
	
	public void naverContentFile(ToonArray target){//Find update,not update and rest 
		String findRest = "휴재";
		String findNew = "\"UP\"";
		
		if(tempWebUrl.contains(findNew)){
			target.state = ToonState.Update;
		}
		else if(tempWebUrl.contains(findRest)){
			target.state = ToonState.Resting;
		}
		else {
			target.state = ToonState.NotUpdate;
		}
		
	}
	/*public void daumFilledUpUpdate(){
		
	}*/
	
	public void naverListUpdate(Integer today){  //Count the number of webtoon and Create ToonArray number     

		naverSubFilled(today);
		String []split = tempWebUrl.split("전체보기");
		
		switch(today){
		case 0:naverSun=new ToonArray[split.length-1];break;//Sunday
		case 1:naverMon=new ToonArray[split.length-1];break;//Monday
		case 2:naverTue=new ToonArray[split.length-1];break;//Tuesday
		case 3:naverWed=new ToonArray[split.length-1];break;//Wednesday
		case 4:naverThu=new ToonArray[split.length-1];break;//Thursday
		case 5:naverFri=new ToonArray[split.length-1];break;//Friday
		case 6:naverSat=new ToonArray[split.length-1];break;//Saturday
		default:break;
		}
	}
	
	public void daumListUpdate(Integer today){ //initialize number of webtoon
			daumSubFilled(today);
			String[] split = tempWebUrl.split("info");
			
			switch(today){
			case 0:daumSun = new ToonArray[split.length-1];break; 
			case 1:daumMon = new ToonArray[split.length-1];break;
			case 2:daumTue = new ToonArray[split.length-1];break;
			case 3:daumWed = new ToonArray[split.length-1];break;
			case 4:daumThu = new ToonArray[split.length-1];break;
			case 5:daumFri = new ToonArray[split.length-1];break;
			case 6:daumSat = new ToonArray[split.length-1];break;
				default:
			}
		
	}
	
	public void nateListUpdate(Integer today){
			nateSubFilled(today);
			String[] split = tempWebUrl.split(" <a href=");
			
			switch(today){
			case 0:nateSun = new ToonArray[split.length-1];break; 
			case 1:nateMon = new ToonArray[split.length-1];break;
			case 2:nateTue = new ToonArray[split.length-1];break;
			case 3:nateWed = new ToonArray[split.length-1];break;
			case 4:nateThu = new ToonArray[split.length-1];break;
			case 5:nateFri = new ToonArray[split.length-1];break;
			case 6:nateSat = new ToonArray[split.length-1];break;
				default:
			}
		
	}
	
	public void naverSubFilled(int today){//divide and fill up url
		int start=0,end=0;
		switch(today){
		case 0:loopUrl(naverSunWeb);break;
		case 1:loopUrl(naverMonWeb);break;
		case 2:loopUrl(naverTueWeb);break;
		case 3:loopUrl(naverWedWeb);break;
		case 4:loopUrl(naverThuWeb);break;
		case 5:loopUrl(naverFriWeb);break;
		case 6:loopUrl(naverSatWeb);break;
		}
		start = tempWebUrl.indexOf("list_area");
		end = tempWebUrl.indexOf("goto_area");
		tempWebUrl = tempWebUrl.substring(start, end);
	}
	
	public void daumSubFilled(int today){ //divide and fill up url
		int start=0,end=0;
		switch(today){
		case 0: loopUrl(daumSunWeb);
				start=tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl6\">");
				end = tempWebUrl.indexOf("<div id=\"daumFoot\"");
        		break;//sunday
        
		case 1: loopUrl(daumMonWeb);
				start=tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl0\">");
		        end = tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl1\">");
		        break;//monday
		        
		case 2: loopUrl(daumTueWeb);
				start=tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl1\">");
				end = tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl2\">");
        		break;//tuesday
        
		case 3: loopUrl(daumWedWeb);
				start=tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl2\">");
        		end = tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl3\">");
        		break;//wednesday
        
		case 4: loopUrl(daumThuWeb);
				start=tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl3\">");
        		end = tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl4\">");
        		break;//thursday
        
		case 5: loopUrl(daumFriWeb);
				start=tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl4\">");
        		end = tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl5\">");
        		break;//friday
        
		case 6: loopUrl(daumSatWeb);
				start=tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl5\">");
				end = tempWebUrl.indexOf("<script type=\"text/html\" id=\"timeline_tmpl6\">");
        		break;//saturday
        		
        		default:
		}
		tempWebUrl = tempWebUrl.substring(start, end);
		
	}

	public void nateSubFilled(int today){
		loopUrl(nate);
		int start=0,end=0;
		
		switch(today){
		case 0:start =tempWebUrl.indexOf("<td class=\"wtl_sun"); end=tempWebUrl.indexOf("</tbody>");break;//Sunday

		case 1:start =tempWebUrl.indexOf("<td class=\"wtl_mon"); end=tempWebUrl.indexOf("<td class=\"wtl_tue");break;//Monday

		case 2:start =tempWebUrl.indexOf("<td class=\"wtl_tue"); end=tempWebUrl.indexOf("<td class=\"wtl_wed");break;//Tuesday

		case 3:start =tempWebUrl.indexOf("<td class=\"wtl_wed"); end=tempWebUrl.indexOf("<td class=\"wtl_thu");break;//Wednesday

		case 4:start =tempWebUrl.indexOf("<td class=\"wtl_thu"); end=tempWebUrl.indexOf("<td class=\"wtl_fri");break;//Thursday

		case 5:start =tempWebUrl.indexOf("<td class=\"wtl_fri"); end=tempWebUrl.indexOf("<td class=\"wtl_sat");break;//Friday

		case 6:start =tempWebUrl.indexOf("<td class=\"wtl_sat"); end=tempWebUrl.indexOf("<td class=\"wtl_sun");break;//Saturday

			default:break;
		}
		tempWebUrl = tempWebUrl.substring(start, end);
		
	}
	
	public void naverFilledUpSite_Day(int today){
		switch(today){
		case 0:totalOfToon=naverSun.length;break;
		case 1:totalOfToon=naverMon.length;break;
		case 2:totalOfToon=naverTue.length;break;
		case 3:totalOfToon=naverWed.length;break;
		case 4:totalOfToon=naverThu.length;break;
		case 5:totalOfToon=naverFri.length;break;
		case 6:totalOfToon=naverSat.length;break;
		}
		
		for(int i =0;i<totalOfToon-1;i++){
			switch(today){
			case 0:naverSun[i].site = NAVER;naverSun[i].week = SUNDAY;break;//Sunday
			case 1:naverMon[i].site = NAVER;naverMon[i].week = MONDAY;break;//Monday
			case 2:naverTue[i].site = NAVER;naverTue[i].week = TUESDAY;break;//Tuesday
			case 3:naverWed[i].site = NAVER;naverWed[i].week = WEDNESDAY;break;//Wednesday
			case 4:naverThu[i].site = NAVER;naverThu[i].week = THURSDAY;break;//Thursday
			case 5:naverFri[i].site = NAVER;naverFri[i].week = FRIDAY;break;//Friday
			case 6:naverSat[i].site = NAVER;naverSat[i].week = SATURDAY;break;//Saturday
			default:
			}
		}
	}
	
	public void daumFilledUpSite_Day(int today){
		switch(today){
		case 0:totalOfToon=daumSun.length;break;
		case 1:totalOfToon=daumMon.length;break;
		case 2:totalOfToon=daumTue.length;break;
		case 3:totalOfToon=daumWed.length;break;
		case 4:totalOfToon=daumThu.length;break;
		case 5:totalOfToon=daumFri.length;break;
		case 6:totalOfToon=daumSat.length;break;
		}
		
		for(int i =0;i<totalOfToon-1;i++){
			switch(today){
			case 0:daumSun[i].site = DAUM;daumSun[i].week = SUNDAY;break;//Sunday
			case 1:daumMon[i].site = DAUM;daumMon[i].week = MONDAY;break;//Monday
			case 2:daumTue[i].site = DAUM;daumTue[i].week = TUESDAY;break;//Tuesday
			case 3:daumWed[i].site = DAUM;daumWed[i].week = WEDNESDAY;break;//Wednesday
			case 4:daumThu[i].site = DAUM;daumThu[i].week = THURSDAY;break;//Thursday
			case 5:daumFri[i].site = DAUM;daumFri[i].week = FRIDAY;break;//Friday
			case 6:daumSat[i].site = DAUM;daumSat[i].week = SATURDAY;break;//Saturday
			default:
			}
		}
	}
	
	public void nateFilledUpSite_Day(int today){
		switch(today){
		case 0:totalOfToon=nateSun.length;break;
		case 1:totalOfToon=nateMon.length;break;
		case 2:totalOfToon=nateTue.length;break;
		case 3:totalOfToon=nateWed.length;break;
		case 4:totalOfToon=nateThu.length;break;
		case 5:totalOfToon=nateFri.length;break;
		case 6:totalOfToon=nateSat.length;break;
		}
		
		for(int i =0;i<totalOfToon-1;i++){
			switch(today){
			case 0:nateSun[i].site = NATE;nateSun[i].week = SUNDAY;break;//Sunday
			case 1:nateMon[i].site = NATE;nateMon[i].week = MONDAY;break;//Monday
			case 2:nateTue[i].site = NATE;nateTue[i].week = TUESDAY;break;//Tuesday
			case 3:nateWed[i].site = NATE;nateWed[i].week = WEDNESDAY;break;//Wednesday
			case 4:nateThu[i].site = NATE;nateThu[i].week = THURSDAY;break;//Thursday
			case 5:nateFri[i].site = NATE;nateFri[i].week = FRIDAY;break;//Friday
			case 6:nateSat[i].site = NATE;nateSat[i].week = SATURDAY;break;//Saturday
				default:
			}
		}
	}
	
	
	WebList(){
		naverMonWeb ="http://comic.naver.com/webtoon/weekdayList.nhn?week=mon";;
		naverTueWeb ="http://comic.naver.com/webtoon/weekdayList.nhn?week=tue";
		naverWedWeb ="http://comic.naver.com/webtoon/weekdayList.nhn?week=wed";
		naverThuWeb ="http://comic.naver.com/webtoon/weekdayList.nhn?week=thu";
		naverFriWeb ="http://comic.naver.com/webtoon/weekdayList.nhn?week=fri";
		naverSatWeb ="http://comic.naver.com/webtoon/weekdayList.nhn?week=sat";
		naverSunWeb ="http://comic.naver.com/webtoon/weekdayList.nhn?week=sun";
		
		daumMonWeb = "http://webtoon.daum.net/webtoon/#mon";
		daumTueWeb = "http://webtoon.daum.net/webtoon/#tue";
		daumWedWeb = "http://webtoon.daum.net/webtoon/#wed";
		daumThuWeb = "http://webtoon.daum.net/webtoon/#thu";
		daumFriWeb = "http://webtoon.daum.net/webtoon/#fri";
		daumSatWeb = "http://webtoon.daum.net/webtoon/#sat";
		daumSunWeb = "http://webtoon.daum.net/webtoon/#sun";
		
		nate = "http://comics.nate.com/webtoon/";
		
		serverState = ServerState.NotInit;

	}
	

}
