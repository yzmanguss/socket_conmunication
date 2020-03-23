package serverSocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HMThread extends Thread {

	Socket socket = null;
	int i = 0;
	BufferedReader bReader;
	DataInputStream dis;
	public HMThread(Socket socket, int i) {
		this.socket = socket;
		this.i = i;

		try {
			//bReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			 dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {
			System.out.println(i + "号线程已开启");
			String contentInfo = null;
			while ((contentInfo = readFromClient()) != null) {
				System.out.println(contentInfo);
				for (int i = 0; i < ServerMain.sockets.size(); i++) {
					if (ServerMain.sockets.get(i) != this.socket) {
						//OutputStream oStream = ServerMain.sockets.get(i).getOutputStream();
						//oStream.write((contentInfo + "\n").getBytes("utf-8"));
						DataOutputStream dos = new DataOutputStream(ServerMain.sockets.get(i).getOutputStream());
						dos.writeUTF(contentInfo);
					}
				}
			}
			// 关闭连接
			// socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取来自客户端的消息
	 * 
	 * @return 客户端发来的消息
	 */
	private String readFromClient() {
		try {
			return dis.readUTF();
			//return bReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 发生错误，移除该socket
			ServerMain.sockets.remove(socket);
		}
		return null;
	}

}
