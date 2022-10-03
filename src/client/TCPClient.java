package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/*
 * Represent implementation of Client in TCP protocol.
 * */

public class TCPClient implements Client {

  private boolean IS_ALIVE = false;
  private Socket socket = null;
  private DataInputStream dis = null;
  private DataOutputStream dos = null;

  @Override
  public void createConnection(String host, int port) throws IOException {
    System.out.println("Server ip : " + InetAddress.getByName(host).toString());
    socket = new Socket(InetAddress.getByName(host), port);
    socket.setSoTimeout(5000);
    dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    dos = new DataOutputStream(socket.getOutputStream());
    IS_ALIVE = !IS_ALIVE;
  }

  @Override
  public void sendRequest(String message) throws IOException {
    dos.writeUTF(message);
  }

  @Override
  public String receiveReply() throws IOException {
    String reply = dis.readUTF();
    return reply;
  }

  @Override
  public void closeConnections() throws IOException {
    dis.close();
    dos.close();
    socket.close();
    IS_ALIVE = !IS_ALIVE;
  }

  @Override
  public boolean isAlive() {
    return IS_ALIVE;
  }
}
