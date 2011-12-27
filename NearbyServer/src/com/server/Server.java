package com.server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.data.*;

public class Server {
	public final static int ECHO_PORT = 6000;
	private ServerSocket serverSock;	//���� ��������
	private Socket clientSock;			//���ο� Ŭ���̾�Ʈ�� ���� ����
	public static HashMap<String, UserInfo> allUserInfo = new HashMap<String, UserInfo>();
	public static HashMap<String, RoomInfo> allRoomInfo = new HashMap<String, RoomInfo>();

	public static void main(String[] args) {
		Server server = new Server();
	}
	
	public Server() {
		try {
			//���������� �����Ͽ� ECHO_PORT�� ��Ʈ�� ����(bind)��Ų��.
			serverSock = new ServerSocket(ECHO_PORT);
			System.out.println("***** Server Open *****");
			
			try {
				while (true) {
					System.out.println("- Server is listening......");
					clientSock = serverSock.accept();
					System.out.println("- Client is found!");
					
					//Ŭ���̾�Ʈ�� ����� ����(clientSock)�� �Ķ���ͷ� ��� Ŭ���̾�Ʈ�� ����� ������(client) ����
					ConThread client = new ConThread(clientSock);
					client.start();
				}
			} catch (IOException e) {
				clientSock.close();
				System.out.println("Client socket is died: " + e);
			} catch (NullPointerException e) {
				clientSock.close();
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}	
}