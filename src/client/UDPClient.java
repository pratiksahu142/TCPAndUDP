package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/*
 * DatagramSocket
 * DatagramPacket(byte[], int length, InetAddress,int serverport )
 * socket.send(DatagramPacket)
 * new DatagramPacket(byte[], int length)
 * socket.receive(Datagram)
 * socket.close()
 * */

public class UDPClient extends AbstractClient {

  private static final Logger logger = Logger.getLogger(UDPClient.class.getName());
  private boolean IS_ALIVE = false;
  private DatagramSocket socket;
  private InetAddress host;
  private int port;

  @Override
  public void createConnection(String ip, int port) throws SocketException, UnknownHostException {
    socket = new DatagramSocket();
    socket.setSoTimeout(5000);
    host = InetAddress.getByName(ip);
    this.port = port;
    IS_ALIVE = !IS_ALIVE;
  }

  @Override
  public void sendRequest(String message) throws IOException {
    byte[] msg = message.getBytes();
    DatagramPacket request = new DatagramPacket(msg, msg.length, host, port);
    socket.send(request);
  }

  @Override
  public String receiveReply() throws IOException {
    byte[] msg = new byte[1000];
    DatagramPacket reply = new DatagramPacket(msg, msg.length);
    socket.receive(reply);
    String response = new String(reply.getData()).trim();
    InetAddress serverAddress = reply.getAddress();
    int serverPort = reply.getPort();
    int responseLength = reply.getLength();
    response =
        "Server at address: \"" + serverAddress.toString() + ":" + serverPort + "\" sent a reply \""
            + response + "\" of length " + responseLength;
    return response;
  }

  @Override
  public void closeConnections() {
    if (socket != null) {
      socket.close();
    }
    IS_ALIVE = !IS_ALIVE;
  }

  @Override
  public boolean isAlive() {
    return IS_ALIVE;
  }
}
