package com.nearby;

import java.util.ArrayList;

import com.data.Packet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChatService extends Activity{

	private Intent intent;
	private Intent serviceIntent;
	public static ArrayList<String> chatList;
	private ArrayAdapter<String> Adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatservice);
		
		//====================================================
		//�����κ��� ���� �޼����� ���� ��̸���Ʈ �غ�
		chatList = new ArrayList<String>();
		// ����� �غ�
		Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chatList);
		// ����� ����
		ListView list = (ListView)findViewById(R.id.lv_ChatPage);
		list.setAdapter(Adapter);
		//=====================================================
		
		//���� ����
		serviceIntent = new Intent(this, RealChatService.class);
		startService(serviceIntent);
	}
	
	//���񽺸� ���� ��Ű�� �޼ҵ�(ä�ù� ������ ���)
	public void serviceDestroy(){
	stopService(serviceIntent);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//����� true�� �� �޴� popup, false�� �� �޴� disappear
		boolean result = super.onCreateOptionsMenu(menu);
		
		// Group ID������ �����ο�, ������ �������� menu��ü�� �߰�
		// �������� ������� 1, 2�� ������ ID�� �ο� �޾Ҵ�.
		// �� ������ 0, 1������ �Ǿ��ִµ� �̴� �տ� ǥ�õ��� �ڿ� ǥ�õ����� �����Ѵ�.
		menu.add(Menu.NONE, 1, 0, "�����ο�");
		menu.add(Menu.NONE, 2, 1, "������");
		
		return result;
	}

	@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	//switch case �� �̿��� �޴� ��� �̺�Ʈ ó��
	    	switch (item.getItemId()) {
	    	case 1:
	    		//���� �ο�
	    		intent = new Intent(this,UserList.class);
	    		startActivity(intent);
	    		return true;
	    	case 2:
	    		//ä�ù� ������
	    		sendExitMessage();
	    		serviceDestroy();
	    		finish();
	    		return true;
	    	}
	    	return (super.onOptionsItemSelected(item));
	}
	public void sendExitMessage(){
	}
}
