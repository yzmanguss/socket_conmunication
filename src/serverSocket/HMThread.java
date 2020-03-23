package serverSocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HMThread extends Thread {

	Socket socket = null;
	int i = 0;
	BufferedReader bReader;
	String nameString;

	public HMThread(Socket socket, int i, String name) {
		this.socket = socket;
		this.i = i;
		this.nameString = name;
		try {
			bReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {
			System.out.println(i + "���߳��ѿ���");
			String contentInfo = null;
			while ((contentInfo = readFromClient()) != null) {
				System.out.println(contentInfo);
				for (int i = 0; i < ServerMain.sockets.size(); i++) {
					if (ServerMain.sockets.get(i) != this.socket) {
						OutputStream oStream = ServerMain.sockets.get(i).getOutputStream();
						oStream.write((nameString + "\n" + contentInfo + "\n").getBytes("utf-8"));
					}
				}
			}
			// �ر�����
			// socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ���Կͻ��˵���Ϣ
	 * 
	 * @return �ͻ��˷�������Ϣ
	 */
	private String readFromClient() {
		try {
			return bReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// ���������Ƴ���socket
			ServerMain.sockets.remove(socket);
		}
		return null;
	}

}
