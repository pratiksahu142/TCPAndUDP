package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPHandler extends AbstractHandler {

  private Socket socket;
  private ServerSocket serverSocket;
  private DataInputStream dis;
  private DataOutputStream dos;
  private InetAddress clientAddress;
  private int clientPort;

  @Override
  public void createServer(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    socket = serverSocket.accept();
    clientAddress = socket.getInetAddress();
    clientPort = socket.getPort();
    dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    dos = new DataOutputStream(socket.getOutputStream());
    setKeyValue(new KeyValue());
  }

  @Override
  public String receiveRequest() throws IOException {
    String request = dis.readUTF();
    return request;
  }

  @Override
  public void sendReply(String reply) throws IOException {
    dos.writeUTF(reply);
  }

  @Override
  public void closeConnections() throws IOException {
    dos.close();
    dis.close();
    socket.close();
    serverSocket.close();
  }

  @Override
  public InetAddress getClientAddress() {
    return clientAddress;
  }

  @Override
  public int getClientPort() {
    return clientPort;
  }
}
