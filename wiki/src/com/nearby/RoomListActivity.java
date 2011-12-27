package com.nearby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.data.Packet;
import com.data.RoomInfo;

public class RoomListActivity extends Activity {

	private Intent intent;
	
	private Button btn_CreateRoom;
	private Button btn_Refresh;
	private ListView lv_AllRoomList;
	private ArrayList<String> roomList = new ArrayList<String>();

	private HashMap<String, RoomInfo> hm_AllRoom;
	private ArrayAdapter<String> adapter;
	public static ArrayList<String> al_AllRoomList = new ArrayList<String>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_roomlist);

		btn_CreateRoom = (Button) findViewById(R.id.btn_CreateRoom);
		btn_Refresh = (Button) findViewById(R.id.btn_Refresh);
		lv_AllRoomList = (ListView) findViewById(R.id.lv_AllRoomList);
		
		// �� ����� ������ �̵� ����Ʈ
		intent = new Intent(this, CreateRoomActivity.class);

		btn_CreateRoom.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				
				startActivity(intent);
			}
		});
		
		//test refresh

				btn_Refresh.setOnClickListener(new Button.OnClickListener() {
					public void onClick(View v) {
						requestRoomList2();
						roomList.clear();
						recvroomlist();
					}
				});
		
	}
	
	@Override
	protected void onResume() {
		
		Toast.makeText(this, "�渮��Ʈ refresh", Toast.LENGTH_SHORT).show();
		requestRoomList2();
		roomList.clear();
		recvroomlist();
		
		super.onResume();
	}

	
/*
	//�α��ν� ������ �ٷ� ��Ŷ�� ������ ��� ����ϴ� ���� �޼ҵ�
	private void requestRoomList() {
		try {
			Packet reRoomPacket = (Packet) MainActivity.in.readObject();
			if (reRoomPacket.getOp() == Packet.ALL_ROOM) {
				Collection<RoomInfo> values = reRoomPacket.getHm_AllRoom().values();
				Iterator<RoomInfo> it = values.iterator();

				while (it.hasNext()) {
					RoomInfo room = it.next();
					String strRoomInfo = "[" + room.getRoomId() + "] " + room.getRoomName();
					roomList.add(strRoomInfo);
				}
				
				adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roomList);
				
				lv_AllRoomList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			
		} catch (OptionalDataException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	//onResume�ϰų� Refresh��ư ������ �� ������ ��Ŷ�� ���� �渮��Ʈ ���� �޾ƿ��� �޼ҵ�
	private void requestRoomList2() {
		Packet refreshRoomPacket = new Packet(Packet.ROOM_REFRESH, null, null, null);
		
		try {
			
			MainActivity.out.writeObject(refreshRoomPacket); // Packet instance ����
			MainActivity.out.flush();
			Log.i("MY PACKET", "������ �ٽ� �޶�� ��Ŷ ���¾��~");
		} catch (IOException e) {
			Log.i("MY PACKET", "������ �ٽ� �޶�� ��Ŷ ���´µ� ���� �Ф�~");
			e.printStackTrace();                                                                                                                                                                                                                                             
		}
		
			/*Packet reRefreshRoomPacket;
			while(true)
			{
			reRefreshRoomPacket = (Packet)MainActivity.in.readObject();
			if(reRefreshRoomPacket.getRoomId()  == MainActivity.myRoomId){
			}
			else
			break;
			}*/
			
			
		
		/*�ѹ� �� readObject�ϴ� ��Ʈ 
		try {

			Packet reRefreshRoomPacket2;
			try {
				reRefreshRoomPacket2 = (Packet) MainActivity.in.readObject();
				
				Log.i("MyLog","��Ŷ�� �ϴ� �޾� �Խ��ϴ�.");
				Log.i("MyLog", "Values: " + reRefreshRoomPacket2.getHm_AllRoom().size());

				//���� ��Ŷ�� �������� ���------------------------------------------------
				Collection<RoomInfo> values2 = reRefreshRoomPacket2.getHm_AllRoom().values();
				Iterator<RoomInfo> it2 = values2.iterator();
				
				while(it2.hasNext()){
					RoomInfo room2 = it2.next();
					Log.i("MyLog","���� �Ϳ� ���� �������� �ֳĸ� -_-����"+ room2.getRoomId() +"// ���� : " + room2.getRoomName());
				}
				//---------------------------------------------------------------
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	void recvroomlist(){

		Packet reRefreshRoomPacket;
		try {
			reRefreshRoomPacket = (Packet) MainActivity.in.readObject();
		
			Log.i("MyLog","��Ŷ�� �ϴ� �޾� �Խ��ϴ�.");
			Log.i("MyLog", "Values: " + reRefreshRoomPacket.getHm_AllRoom().size());

			//���� ��Ŷ�� �������� ���------------------------------------------------
			Collection<RoomInfo> values2 = reRefreshRoomPacket.getHm_AllRoom().values();
			Iterator<RoomInfo> it2 = values2.iterator();
		
			while(it2.hasNext()){
				RoomInfo room2 = it2.next();
				Log.i("MyLog","���� �Ϳ� ���� �������� �ֳĸ� -_-����"+ room2.getRoomId() +"// ���� : " + room2.getRoomName());
			}
			//---------------------------------------------------------------
		
		
			if (reRefreshRoomPacket.getOp() == Packet.ROOM_REFRESH) {
				Collection<RoomInfo> values = reRefreshRoomPacket.getHm_AllRoom().values();
				Iterator<RoomInfo> it = values.iterator();
			
				while (it.hasNext()) {
					RoomInfo room = it.next();
					String strRoomInfo = "[" + room.getRoomId() + "] " + room.getRoomName();
					roomList.add(strRoomInfo);
					Log.i("MyLog","�����ʹ� �ϴ� �޾� �Խ��ϴ�.");
				}
			
				adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roomList);
				Log.i("MyLog", "Count: " + adapter.getCount());
				lv_AllRoomList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
