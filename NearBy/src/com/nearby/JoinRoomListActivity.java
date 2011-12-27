package com.nearby;

import java.io.IOException;
import java.io.OptionalDataException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import com.data.Packet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class JoinRoomListActivity extends Activity {
	
	private ArrayList<String> joinRoom;
	private ArrayAdapter<String> adapter;
	private ListView lv_joinRoom;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_joinroomlist);
		
		lv_joinRoom = (ListView)findViewById(R.id.lv_JoinRoomList);
	}

	@Override
	protected void onResume() {

		Toast.makeText(this, "������ �渮��Ʈ refresh", Toast.LENGTH_SHORT).show();
		requestJoinRoomList();
		super.onResume();
	}

	public void requestJoinRoomList() {
		//�����ϰ� �ִ� �� ������ �ޱ� ���� ��Ŷ�� ������.
		Packet refreshJoinRoomPacket = new Packet(Packet.JOIN_ROOMREFRESH, null, MainActivity.phoneNumber, null);
		Log.i("My Log", "���� ������ ���� ������ ��û �մϴ�.");
		
		try {
			MainActivity.out.writeObject(refreshJoinRoomPacket);
			Log.i("MY PACKET", "���� �����ϰ� �ִ� ������ �ٽ� �޶�� ��Ŷ ���¾��~");
		} catch (IOException e) {
			Log.i("MY PACKET", "���� �����ϰ� �ִ� ������ �ٽ� �޶�� ��Ŷ ���´µ� ���� �Ф�~");
			e.printStackTrace();
		}
		
		//������ �ؾ� �� ��
		try {
			Packet reRefreshJoinRoomPacket = (Packet) MainActivity.in.readObject();
			joinRoom = reRefreshJoinRoomPacket.getJoinRoom();
			Log.i("My Log", "���� ������ ���� ������ �����κ��� �޾ҽ��ϴ�.");
			Iterator<String> itTest = joinRoom.iterator();
			while(itTest.hasNext()){
				String test = itTest.next();
				Log.i("My Log", test);
			}
			
		} catch (OptionalDataException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		joinRoom.clear();
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, joinRoom);
		lv_joinRoom.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}
