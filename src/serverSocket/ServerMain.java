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
			serverSocket = new ServerSocket(20000);
			
			// 2.���տͻ��˵�����
			
			while(true){
				Socket socket = serverSocket.accept();
				socket.setKeepAlive(true);
				// 3.��ȡ����
				BufferedReader bReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
				sockets.add(socket);
				String nameString = bReader.readLine();
				System.out.println("��"+ i +"λ�ͻ������ӳɹ�");
				new HMThread(socket, i++,nameString).start();
				System.out.println("��"+ i +"λ�ͻ������ӳɹ�");
				System.out.println("��"+ i +"λ�ͻ������ӳɹ�");
				System.out.println("��"+ i +"λ�ͻ������ӳɹ�");
				System.out.println("��"+ i +"λ�ͻ������ӳɹ�");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
