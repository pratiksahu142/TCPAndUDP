package server;

import java.io.IOException;
import java.net.InetAddress;

/*
 * Represents an interface to implement Server application for TCP and UDP protocols.
 * */
public interface Server {

  /**
   * Initiates connection of the client.
   *
   * @param port to listen to client's requests on
   * @throws IOException when either host is down or port is unavavilable/wrong
   */
  void createServer(int port) throws IOException;

  /**
   * Receives the request from client and prints it on the console.
   *
   * @return client request if valid otherwise, logs and returns malformed packet message
   * @throws IOException when unable to receive request from client
   */
  String receiveRequest() throws IOException;

  /**
   * Performs operations required by the key-value store like PUT, GET, DELETE.
   *
   * @param request string received from the client
   * @return response after performing desired operation
   */
  String performOperation(String request);

  /**
   * Sends response to the client after operation has been performed on the received request.
   *
   * @param reply string to be sent to the client
   * @throws IOException when unable to send to the client
   */
  void sendReply(String reply) throws IOException;

  /**
   * Closes the sockets, data input and output streams opened.
   *
   * @throws IOException unable to close the connections.
   */
  void closeConnections() throws IOException;

  /**
   * Fetches the IP address of the client which sent the request.
   *
   * @return IP address of client
   */
  InetAddress getClientAddress();

  /**
   * Fetches port number of client from which request came in.
   *
   * @return port number of client
   */
  int getClientPort();
}
