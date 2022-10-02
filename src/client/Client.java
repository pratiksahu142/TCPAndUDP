package client;



/*
 * DatagramSocket()
 * DatagramPacket(byte[], int length, InetAddress,int serverport )
 * socket.send(DatagramPacket)
 * new DatagramPacket(byte[], int length)
 * socket.receive(Datagram)
 * socket.close()
 * */

/*
 * Socket(String ip, int port)
 * DataInputStream(socket)
 * DataInputStream1(System.in)
 * DataOuputStream
 * dis.readLine()
 * dos.writeUTF(String)
 * dis.close()
 * dis1.close()
 * dos.close()
 * socket.close()
 * */

import java.io.IOException;

public interface Client {

  void createConnection(String host, int port) throws IOException;

  void sendRequest(String message) throws IOException;

  String receiveReply() throws IOException;

  void closeConnections() throws IOException;

  boolean isAlive();
}
