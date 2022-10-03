package client;

import java.io.IOException;

/*
 * Represents an interface to implement client application for TCP and UDP protocols.
 * */
public interface Client {

  /**
   * Initiates connection of the client.
   *
   * @param host ip address of the server
   * @param port to listen/forward requests to the server
   * @throws IOException when either host is down or port is unavavilable/wrong
   */
  void createConnection(String host, int port) throws IOException;

  /**
   * Prepares and sends the request message then sends it to the server.
   *
   * @param message request to be sent from client to server
   * @throws IOException when unable to send request to server
   */
  void sendRequest(String message) throws IOException;

  /**
   * Receives the response back from server and prints it on the console.
   *
   * @return response from server back to driver program.
   * @throws IOException when unable to receive response from server
   */
  String receiveReply() throws IOException;

  /**
   * Closes the sockets, data input and output streams opened.
   *
   * @throws IOException unable to close the connections.
   */
  void closeConnections() throws IOException;

  /**
   * Checks if client is running or not.
   *
   * @return true if client running otherwise, false.
   */
  boolean isAlive();
}
