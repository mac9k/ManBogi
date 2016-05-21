package com.example.manbogi;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.ref.SoftReference;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MyServer {
	public static void main(String[] args){

		WebList webToon = new WebList();
		
		TCPServer mThread = new TCPServer(webToon);
		Thread _mThread = new Thread(mThread);

		TimeThread tThread = new TimeThread(webToon);
		Thread _tThread = new Thread(tThread);
		
		UpdateThread uThread = new UpdateThread(webToon);
		Thread _uThread = new Thread(uThread);
		
		System.out.println("Threads Start!");
		
		
		_uThread.start();//Update Thread
		_mThread.start();//TCP Thread
		_tThread.start();//TIme Thread
		
		
		//Timer timer = new Timer();
		//timerThread timerThread = new timerThread();
		//timer.schedule(timerThread, 10000,30000);
		//3000 3sec, 30000 30sec 
		

	}

	public static void timeCheck(WebList webToon){
		
		GregorianCalendar calendar = new GregorianCalendar();
		Calendar oCalendar = Calendar.getInstance();
		
		//int year = oCalendar.get(oCalendar.YEAR);
		//int month = oCalendar.get(oCalendar.MONTH)+1;
		//int date = oCalendar.get(oCalendar.DATE);
		int day = oCalendar.get(Calendar.DAY_OF_WEEK)-1;
		int hour = oCalendar.get(oCalendar.HOUR_OF_DAY);
		int min = oCalendar.get(oCalendar.MINUTE);
		
		switch(day){ ///////////////need to upgrade code , URL,name,update only naver
			case 0:System.out.println("Sun"+hour+":"+min +",naver");//Sunday
			//webToon.loopUrl(webToon.naverSunWeb);
			//webToon.naverFilledUpUpdate(day);
			break;
			
			case 1:System.out.println("Mon"+hour+":"+min +",naver");//Monday
			//webToon.loopUrl(webToon.naverMonWeb);
			//webToon.naverFilledUpUpdate(day);
			break;
			
			case 2:System.out.println("Tue"+hour+":"+min +",naver");//Tuesday
			//webToon.loopUrl(webToon.naverTueWeb);
			//webToon.naverFilledUpUpdate(day);
			break;
			
			case 3:System.out.println("Wed"+hour+":"+min +",naver");//Wednesday
			//webToon.loopUrl(webToon.naverWedWeb);
			//webToon.naverFilledUpUpdate(day);
			break;
			
			case 4:System.out.println("Thu"+hour+":"+min +",naver");//Thursday
			//webToon.loopUrl(webToon.naverThuWeb);
			//webToon.naverFilledUpUpdate(day);
			break;
			
			case 5:System.out.println("Fri"+hour+":"+min +",naver");//Friday
			//webToon.loopUrl(webToon.naverFriWeb);
			//webToon.naverFilledUpUpdate(day);
			break;
					
			case 6:System.out.println("Sat"+hour+":"+min +",naver");//Saturday
			//webToon.loopUrl(webToon.naverSatWeb);
			//webToon.naverFilledUpUpdate(day);
			break;
			
			default: break;
		}
		System.out.println("----------------------------------------");
		
		/*if(hour >= 23){
		switch(day){ ///////////////need to upgrade code, 
		case 0://Sundat to Monday
		webToon.loopUrl(webToon.naverMonWeb);
		webToon.naverFilledUpURL(1);webToon.naverFilledUpName(1);webToon.naverFilledUpUpdate(1);break;
		
		case 1://Monday to Tuesday
		webToon.loopUrl(webToon.naverTueWeb);
		webToon.naverFilledUpURL(2);webToon.naverFilledUpName(2);webToon.naverFilledUpUpdate(2);break;
		
		case 2://Tuesday to Wednesday
		webToon.loopUrl(webToon.naverWedWeb);
		webToon.naverFilledUpURL(3);webToon.naverFilledUpName(3);webToon.naverFilledUpUpdate(3);break;
		
		case 3://Wednesday to Thursday
		webToon.loopUrl(webToon.naverThuWeb);
		webToon.naverFilledUpURL(4);webToon.naverFilledUpName(4);webToon.naverFilledUpUpdate(4);break;
		
		case 4://Thursday to Friday
		webToon.loopUrl(webToon.naverFriWeb);
		webToon.naverFilledUpURL(5);webToon.naverFilledUpName(5);webToon.naverFilledUpUpdate(5);break;
		
		case 5://Friday to Saturday
		webToon.loopUrl(webToon.naverSatWeb);
		webToon.naverFilledUpURL(6);webToon.naverFilledUpName(6);webToon.naverFilledUpUpdate(6);break;
				
		case 6://Saturday to Sunday
		webToon.loopUrl(webToon.naverSunWeb);
		webToon.naverFilledUpURL(0);webToon.naverFilledUpName(0);webToon.naverFilledUpUpdate(0);break;
		
		default: break;
			}
		}
		
		System.out.println("----------------------------------------");*/
		
		switch(day){ ///////////////need to upgrade code , URL,name,update only daum
		case 0:System.out.println("Sun"+hour+":"+min +",daum");//Sunday
		//webToon.loopUrl(webToon.daumSunWeb);
		//webToon.daumFilledUpURL(day);webToon.daumFilledUpName(day);
		break;
		
		case 1:System.out.println("Mon"+hour+":"+min +",daum");//Monday
		//webToon.loopUrl(webToon.daumMonWeb);
		//webToon.daumFilledUpURL(day);webToon.daumFilledUpName(day);
		break;
		
		case 2:System.out.println("Tue"+hour+":"+min +",daum");//Tuesday
		//webToon.loopUrl(webToon.daumTueWeb);
		//webToon.daumFilledUpURL(day);webToon.daumFilledUpName(day);
		break;
		
		case 3:System.out.println("Wed"+hour+":"+min +",daum");//Wednesday
		//webToon.loopUrl(webToon.daumWedWeb);
		//webToon.daumFilledUpURL(day);webToon.daumFilledUpName(day);
		break;
		
		case 4:System.out.println("Thu"+hour+":"+min +",daum");//Thursday
		//webToon.loopUrl(webToon.daumThuWeb);
		//webToon.daumFilledUpURL(day);webToon.daumFilledUpName(day);
		break;
		
		case 5:System.out.println("Fri"+hour+":"+min +",daum");//Friday
		//webToon.loopUrl(webToon.daumFriWeb);
		//webToon.daumFilledUpURL(day);webToon.daumFilledUpName(day);
		break;
				
		case 6:System.out.println("Sat"+hour+":"+min +",daum");//Saturday
		//webToon.loopUrl(webToon.daumSatWeb);
		//webToon.daumFilledUpURL(day);webToon.daumFilledUpName(day);
		break;
		
		default: break;
		}
		
		System.out.println("----------------------------------------");
		switch(day){ ///////////////need to upgrade code , URL,name,update only nate
		case 0:System.out.println("Sun"+hour+":"+min +",nate");//Sunday
		//webToon.loopUrl(webToon.nateSunWeb);
		//webToon.nateFilledUpURL(day);webToon.nateFilledUpName(day);
		break;
		
		case 1:System.out.println("Mon"+hour+":"+min +",nate");//Monday
		//webToon.loopUrl(webToon.nateMonWeb);
		//webToon.nateFilledUpURL(day);webToon.nateFilledUpName(day);
		break;
		//
		case 2:System.out.println("Tue"+hour+":"+min +",nate");//Tuesday
		//webToon.loopUrl(webToon.nateTueWeb);
		//webToon.nateFilledUpURL(day);webToon.nateFilledUpName(day);
		break;
		
		case 3:System.out.println("Wed"+hour+":"+min +",nate");//Wednesday
		//webToon.loopUrl(webToon.nateWedWeb);
		//webToon.nateFilledUpURL(day);webToon.nateFilledUpName(day);
		break;
		
		case 4:System.out.println("Thu"+hour+":"+min +",nate");//Thursday
		//webToon.loopUrl(webToon.nateThuWeb);
		//webToon.nateFilledUpURL(day);webToon.nateFilledUpName(day);
		break;
		
		case 5:System.out.println("Fri"+hour+":"+min +",nate");//Friday
		//webToon.loopUrl(webToon.nateFriWeb);
		//webToon.nateFilledUpURL(day);webToon.nateFilledUpName(day);
		break;
				
		case 6:System.out.println("Sat"+hour+":"+min +",nate");//Saturday
		//webToon.loopUrl(webToon.nateSatWeb);
		//webToon.nateFilledUpURL(day);webToon.nateFilledUpName(day);
		break;
		
		default: break;
		}
	}
	
}

class TimeThread implements  Runnable{
	
	private WebList webToon;
	public TimeThread(WebList webToon){
		this.webToon = webToon;
	}
	public void run(){
			while(true){
			try{
				Thread.sleep(100000);//1.6min
			
				if(webToon.serverState == ServerState.Init){
					System.out.println("TimeThread ing...");
					MyServer.timeCheck(webToon);
				}
				else
					System.out.println("State is NotInit");
			}catch (InterruptedException e) {
				System.out.println("TimeThread Err");
				e.printStackTrace();
			}catch(Exception e){
				System.out.println("TimeThread Err");
				e.printStackTrace();
			}
		}
	}
}

class UpdateThread implements Runnable{ //choose thread or timer   ,if thread,   synchronized
	
	private WebList webToon;
	public UpdateThread(WebList webToon){
		this.webToon = webToon;
	}
	@Override
	public void run() {
			while(true){
			try{
				//if(webToon.serverState == ServerState.NotInit){
				webToon.serverState = ServerState.NotInit;
					webToon.serverStart();
					webToon.tempAllVar();
				webToon.serverState = ServerState.Init;
				//}
				System.out.println("UpdateThread...Done");
				Thread.sleep(1000000);//166min
			}catch (InterruptedException e) {
				System.out.println("UpdateThread Err");
				e.printStackTrace();
			}catch(Exception e){
				System.out.println("UpdateThread Err");
				e.printStackTrace();
			}
		}
	}
}

class TCPServer implements Runnable{
	private static final int serverPort = 8989;
	private static String day,web; 
	private WebList webToon;
	
	public TCPServer(WebList webToon){
		this.webToon = webToon;
	}
	public void run() {
		try{
			ServerSocket serverSocket = new ServerSocket(serverPort);
			ObjectOutputStream output = null;
			ObjectInputStream input = null;
			while(true){
				//if(webToon.serverState == ServerState.Init){
				
				Socket client = serverSocket.accept();
				System.out.println("Connect");
				try{
					
					input = new ObjectInputStream(client.getInputStream());
					String str = (String)input.readObject();
					System.out.println("S : Received : "+ str);//
					
					StringTokenizer token = new StringTokenizer(str,",");

					if (token.hasMoreElements()) {
						day = token.nextToken();
						web = token.nextToken();
						ToonArray[] result = sendMessage(day, web);// ToonArray[]

						Object obj = result;
						output = new ObjectOutputStream(client.getOutputStream());
						output.flush();
						output.writeObject(obj);
						output.flush();// result[0].url
						output.close();
						}
				}catch (Exception e) {
                    System.out.println("ServerThread Err");
                    e.printStackTrace();
                    
                }finally { 
                    client.close();
                    System.out.println("ServerSocket: Close.");
                }

			//}// if-ServerState.Init
		}
			
		}catch(Exception e){
			System.out.println("S: Error");
            e.printStackTrace();
		}
	}
	
	public ToonArray[] sendMessage(String day, String web){//Choose the variable condition(class , variable)
	
		final int NAVER = 0;
		final int DAUM = 1;
		final int NATE = 2;
		final int OLLEH = 3;
		
		if(Integer.parseInt(web) == NAVER){
			switch(day){
				case "0":return webToon.tempNaverSun;//Sunday
				case "1":return webToon.tempNaverMon;//Monday
				case "2":return webToon.tempNaverTue;//Tuesday
				case "3":return webToon.tempNaverWed;//Wednesday
				case "4":return webToon.tempNaverThu;//Thursday
				case "5":return webToon.tempNaverFri;//Friday
				case "6":return webToon.tempNaverSat;//Saturday
				}
		}
		
		if(Integer.parseInt(web) == DAUM){
			switch(day){
				case "0":return webToon.tempDaumSun;//Sunday
				case "1":return webToon.tempDaumMon;//Monday
				case "2":return webToon.tempDaumTue;//Tuesday
				case "3":return webToon.tempDaumWed;//Wednesday
				case "4":return webToon.tempDaumThu;//Thursday
				case "5":return webToon.tempDaumFri;//Friday
				case "6":return webToon.tempDaumSat;//Saturday
				}
		}
		
		if(Integer.parseInt(web) == NATE){
			switch(day){
				case "0":return webToon.tempNateSun;//Sunday
				case "1":return webToon.tempNateMon;//Monday
				case "2":return webToon.tempNateTue;//Tuesday
				case "3":return webToon.tempNateWed;//Wednesday
				case "4":return webToon.tempNateThu;//Thursday
				case "5":return webToon.tempNateFri;//Friday
				case "6":return webToon.tempNateSat;//Saturday
				}
		}
		return null;
	}
}


