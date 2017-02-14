package io.netty.example.discard.socket.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * 
 * @author Administrator
 * socket客户端访问netty服务器端
 */
public class DiscardSocketClient {

	public static void main(String[] args) throws IOException, InterruptedException {
		String host="127.0.0.1";
		int port=8080;
		Socket socket = new Socket(host,port);
		// 设置InputStream上调用 read()阻塞超时时间2秒
		socket.setSoTimeout(45000);
		// 设置socket发包缓冲为1k；
		socket.setSendBufferSize(1 * 1024);
		// 设置socket底层接收缓冲为1k
		socket.setReceiveBufferSize(1 * 1024);
		// 关闭Nagle算法.立即发包
		socket.setTcpNoDelay(true);
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
		dout.write("ssss".getBytes());
		dout.flush();
		Thread.sleep(5000);
		dout.close();
		socket.close();
	}
}
