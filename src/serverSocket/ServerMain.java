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
			
			// 1.创建服务器
			serverSocket = new ServerSocket(20010);
			System.out.println("等待连接。。。。");
			// 2.接收客户端的连接
			
			while(true){
				Socket socket = serverSocket.accept();
				socket.setKeepAlive(true);
				sockets.add(socket);
				
				System.out.println("第"+ i +"位客户端连接成功");
				new HMThread(socket, i++).start();
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
