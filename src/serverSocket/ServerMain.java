package serverSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {

	private static ServerSocket serverSocket;
	public static ArrayList<Socket> sockets = new ArrayList<Socket>();
	static int i = 1;
	public static void main(String[] args) {
		
		try {
			
			// 1.����������
			serverSocket = new ServerSocket(20010);
			System.out.println("�ȴ����ӡ�������");
			// 2.���տͻ��˵�����
			
			while(true){
				Socket socket = serverSocket.accept();
				socket.setKeepAlive(true);
				sockets.add(socket);
				
				System.out.println("��"+ i +"λ�ͻ������ӳɹ�");
				new HMThread(socket, i++).start();
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
