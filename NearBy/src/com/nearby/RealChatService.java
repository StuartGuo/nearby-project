package com.nearby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.*;
import android.widget.ListView;

public class RealChatService extends Service {
	private BufferedReader reader;
	private Boolean receiveYorN = true;
	
	@Override
	public void onCreate(){
		Log.i("My Tag","���� ����~");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.i("My Tag","onStartCommand, Received start id"+startId+intent);

		sendTest();
		sendTest2();
		sendTest();
		sendTest2();
		sendTest();
		sendTest2();
		sendTest();
		sendTest2();

		return START_STICKY;
	}
	
	@Override
	public void onDestroy(){
		Log.i("My Tag","���� ����~");
		receiveYorN = false;
	}
	
	@Override
	public IBinder onBind(Intent intent){
		Log.i("My Tag","���� onBind");
		return null;
		
	}
	
	public void sendTest(){
		ChatService.chatList.add("�ȳ��ϼ���?");
	}
	public void sendTest2(){
		ChatService.chatList.add("�ݰ����ϴ٤���");
	}
	/*
	public void receiveToServer(){
		while(receiveYorN){
			try {
				reader = new BufferedReader(new InputStreamReader(MainActivity.clientSocket.getInputStream()));
				String inputString = reader.readLine();
				if(inputString.startsWith("MSG")){
					ChatService.chatList.add(inputString);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
}