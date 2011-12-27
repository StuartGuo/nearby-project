package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.data.Packet;
import com.data.RoomInfo;
import com.data.UserInfo;

public class ConThread extends Thread {
	
	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private Packet packet;
	
	private String roomId;
	private String roomName;
	private ArrayList<String> joinRoom = new ArrayList<String>();
	
	private String phoneNumber;
	private String nickName;
	private static int roomCount = 0;

	//refreshJoinRoom������
	private RoomInfo roomPack;
	private ArrayList<UserInfo> userSet;
	private Iterator<UserInfo> userIt;
	private UserInfo userPack;
	
	public ConThread(Socket clientSock) {
		this.clientSocket = clientSock;

		try {
			in = new ObjectInputStream(this.clientSocket.getInputStream());
			out = new ObjectOutputStream(this.clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				
				System.out.println("\n<Client Start>");
				packet = (Packet) in.readObject();
				System.out.println("Ŭ���̾�Ʈ���� ��Ŷ�� �޾ҽ��ϴ� �̰� �����?");
				
				if (packet.getOp() == Packet.USER_PACKET) {
					System.out.println("����� ���ø����̼� ����");
					enrollUser();
				}
				
				else if (packet.getOp() == Packet.ROOM_PACKET) {
					System.out.println("������� �� ��� ��û");
					createRoom();
				}
				else if(packet.getOp() == Packet.ROOM_REFRESH){
					System.out.println("������� �� Refresh ��û");
					refreshRoom();
				}else if(packet.getOp() == Packet.JOIN_ROOMREFRESH){
					System.out.println("����ڰ� ������ �� Refresh ��û");
					refreshjoinRoom();
				}
				
				TEST_METHOD_ALL_USER_VIEW();

			} catch (ClassNotFoundException e) {
				System.out.println("���� �����ϱ�...?");

				break;
			} catch (IOException e) {
				System.out.println("����ڰ� ���ø����̼��� ���� �߽��ϴ�.");
				break;
			}
		}
		try {
			out.close();
			in.close();
			this.clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Server.allUserInfo.remove(phoneNumber);
	}

	// ����� ��� �޼���
	private void enrollUser() {
		phoneNumber = packet.getPhoneNumber();
		nickName = packet.getNickName();
		
		UserInfo tmpUser = new UserInfo(phoneNumber, nickName);	// OK!
		Server.allUserInfo.put(packet.getPhoneNumber(), tmpUser);						// OK!
	
		//TEST_ROOMMAKE();

	/*	Packet pAllRoom = new Packet(Packet.ALL_ROOM, Server.allRoomInfo);
		
		try {
			out.writeObject(pAllRoom);
			System.out.println("������ ���ͼ� ��� �� ���� �۽��߾�䤾��");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	// �� ���� �޼���
	private void createRoom() {
		roomId = "rid" + roomCount; // Room Id
		roomCount++;
		roomName = packet.getRoomName();
		phoneNumber = packet.getPhoneNumber();
		
		UserInfo roomMaker = Server.allUserInfo.get(phoneNumber);
		RoomInfo newRoom = new RoomInfo(roomId, roomName, roomMaker);
		
		Server.allRoomInfo.put(roomId, newRoom);

		System.out.println("Room Name is : " + roomName +"�̶�� ���� "+ phoneNumber +"�� ��������!");
	}
	
	private void TEST_METHOD_ALL_USER_VIEW() {
		Collection<UserInfo> allUser = Server.allUserInfo.values();
		Iterator<UserInfo> it = allUser.iterator();
		System.out.println("���� �������� ������...");

		while (it.hasNext()) {
			UserInfo user = (UserInfo) it.next();
			System.out.println("nick name: " + user.getNickName() + ", phone number" + user.getPhoneNumber());
		}
	}
	
	private void TEST_ROOMMAKE() {
		UserInfo user1 = new UserInfo("01011111111", "yoonhok");
		RoomInfo room1 = new RoomInfo("rid001", "���� ��� �𿩶�~", user1);
		Server.allRoomInfo.put("rid001", room1);
		
		UserInfo user2 = new UserInfo("01022222222", "yoonhok2");
		RoomInfo room2 = new RoomInfo("rid002", "�ܱ����л���!", user2);
		Server.allRoomInfo.put("rid002", room2);

		UserInfo user3 = new UserInfo("01033333333", "yoonhok3");
		RoomInfo room3 = new RoomInfo("rid003", "�ɽ���~", user3);
		Server.allRoomInfo.put("rid003", room3);
	}
	
	
	
	// �� Refresh �޼���
	private void refreshRoom() {
		//���� �����ϴ� ��� �� ���-----------------------------------
		Collection<RoomInfo> allRoom= Server.allRoomInfo.values();

		Iterator<RoomInfo> it = allRoom.iterator();
		while(it.hasNext()){
			RoomInfo room = it.next();
			System.out.println("���� �Ϳ� ���� �������� �ֳĸ� -_-����"+ room.getRoomId() +"// ���� : " + room.getRoomName());
		}
		System.out.println("----------------------------------------------------------------------");
		//------------------------------------------------------
		
		
		//���� refresh���� ��Ŷ ����
		Packet reRefreshRoomPacket = new Packet(Packet.ROOM_REFRESH, Server.allRoomInfo);
		
		
		try {

			out.writeObject(reRefreshRoomPacket);
			out.flush();
			System.out.println("�� ���� refresh�ؼ� �۽��߽��ϴ�~ " + reRefreshRoomPacket.getHm_AllRoom().size());
			//���� ��Ŷ �м�------------------------------------------------
			Collection<RoomInfo> values = reRefreshRoomPacket.getHm_AllRoom().values();
			Iterator<RoomInfo> it2 = values.iterator();
			
			while(it2.hasNext()){
				RoomInfo room2 = it2.next();
				System.out.println("���� �Ϳ� ���� �������� �ֳĸ� -_-����"+ room2.getRoomId() +"// ���� : " + room2.getRoomName());
			}
			
			System.out.println("----------------------------------------------------------------------");
			//------------------------------------------------------------
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void refreshjoinRoom(){
		
		String phoneNumber = packet.getPhoneNumber();
		
		Collection<RoomInfo> allRoom= Server.allRoomInfo.values();
		Iterator<RoomInfo> roomIt = allRoom.iterator();

		//����ڰ� ������ ���� ����� ��� ���� �� �ݷ����� ����.
		while(roomIt.hasNext()){

			roomPack = roomIt.next();	//�ϳ��� ���ѿ� �ִ´�
			System.out.println("�� ���̸��� "+roomPack.getRoomName());
			userSet = roomPack.getUserList();	//�� ���� ��������Ʈ ����
			userIt = userSet.iterator();		//���ͷ����Ϳ� �ִ´�
			//���� ��̸���Ʈ�� ����.
			while(userIt.hasNext()){
				userPack = userIt.next();	//�ϳ��� �����ѿ� �ִ´�
				System.out.println("�� ������ ����ȣ�� "+userPack.getPhoneNumber());
				if(phoneNumber == userPack.getPhoneNumber()){
					//�� ���� ����� ����ȣ�� ���Ͽ� �ش� �濡 ������ ����Ʈ�� add
					System.out.println("�� ���� ������!!");
					joinRoom.add(roomPack.getRoomName());
					System.out.println(roomPack.getRoomName());
				}else{
					System.out.println("�� ������ �� �濡 ����...");
				}
			}
		}
		
		//joinroom�� Refresh���� ��Ŷ�� ����
		Packet reRefreshJoinRoomPacket = new Packet(Packet.JOIN_ROOMREFRESH, null, null, joinRoom);
		try {
			out.writeObject(reRefreshJoinRoomPacket);
			System.out.println("�� ���� ������ Refresh�߽��ϴ�.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
