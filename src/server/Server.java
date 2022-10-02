package server;

/*
 * ServerSocket(port)
 * socket
 * dis
 * dos
 * dis.readUTF()
 * dos.writeUTF(String)
 * dis.close
 * dos.close
 * socket.close
 * serversocket.close
 * */

/*
 * DatagramSocket(port)
 * DatagramPacket(byte[], length) // request
 * socket.receive(DatagramPacket)
 * DatagramPacket(byte[], int length, InetAddress,int serverport ) //reply
 * socket.send(DatagramPacket)
 * socket.close()
 * */

import java.io.IOException;
import java.net.InetAddress;

public interface Server {

  void createServer(int port) throws IOException;

  String receiveRequest() throws IOException;

  void sendReply(String reply) throws IOException;

  void closeConnections() throws IOException;

  String performOperation(String request);

  InetAddress getClientAddress();

  int getClientPort();
}
