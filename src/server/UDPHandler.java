package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

public class UDPHandler extends AbstractHandler {


  private static final Logger logger = Logger.getLogger(UDPHandler.class.getName());
  private DatagramSocket socket;
  private InetAddress clientAddress;
  private int clientPort;

  /*
   * DatagramSocket(port)
   * DatagramPacket(byte[], length) // request
   * socket.receive(DatagramPacket)
   * DatagramPacket(byte[], int length, InetAddress,int serverport ) //reply
   * socket.send(DatagramPacket)
   * socket.close()
   * */
  @Override
  public void createServer(int port) throws IOException {
    socket = new DatagramSocket(port);
    setKeyValue(new KeyValue());
  }

  @Override
  public String receiveRequest() throws IOException {
    byte[] msg = new byte[1000];
    DatagramPacket request = new DatagramPacket(msg, msg.length);
    socket.receive(request);
    String requestStr = new String(request.getData()).trim();
    this.clientAddress = request.getAddress();
    this.clientPort = request.getPort();
    int requestLength = request.getLength();
    logger.info(
        "Client at address: \"" + clientAddress.toString() + ":" + clientPort
            + "\" sent a request : \""
            + requestStr + "\" of length " + requestLength);
    return requestStr;
  }

  @Override
  public void sendReply(String reply) throws IOException {
    DatagramPacket response = new DatagramPacket(reply.getBytes(), reply.length(), clientAddress,
        clientPort);
    socket.send(response);
  }

  @Override
  public void closeConnections() throws IOException {
    if (socket != null) {
      socket.close();
    }
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
