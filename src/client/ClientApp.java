package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Represents the driver application for client implementation that handles working of client with
 * TCP and UDP both the protocols.
 */
public class ClientApp {

  public static final Logger logger = Logger.getLogger(ClientApp.class.getName());
  private static final String EXIT = "exit";
  private static final String TCP = "tcp";
  private static final String UDP = "udp";
  private static final String REQUEST_FOR_INPUT =
      "What operation do you want to do?" + "\nPUT:<key>:<value>" + "\nGET:<key>"
          + "\nDELETE:<key>\n";

  /**
   * Main driver method that invokes client and drives it.
   *
   * @param args contains ip address of server, port of server and protocol to be invoked separated
   *             by space.
   * @throws IllegalArgumentException if any of the above are not present as arguments
   * @throws IOException              if connection disrupts
   */
  public static void main(String[] args) throws IllegalArgumentException, IOException {
    Scanner sc = new Scanner(System.in);
    String ip = args[0];
    int port = Integer.parseInt(args[1]);
    String protocol = args[2];
    Client client = null;

    if (protocol.equalsIgnoreCase(TCP)) {
      handleTCPClient(sc, ip, port);
    } else if (protocol.equalsIgnoreCase(UDP)) {
      handleUDPClient(sc, ip, port);
    } else {
      throw new IllegalArgumentException("No valid protocol (tcp/udp).");
    }

    logger.info("Closing Client App...");
  }

  private static void handleUDPClient(Scanner sc, String ip, int port) throws IOException {
    Client client = null;
    // For demo of 5 PUTs, 5 GETs and 5 DELETEs plus some additional cases that I prepared.
    performInitialOperationsOnClient(client, ip, port, UDP);
    while (true) {
      logger.info(REQUEST_FOR_INPUT);
      String request = sc.nextLine();
      if (request.equalsIgnoreCase(EXIT)) {
        break;
      }
      client = performUDPClientOperation(ip, port, request);
    }
    if (client != null) {
      client.closeConnections();
    }
  }

  private static Client performUDPClientOperation(String ip, int port, String request)
      throws IOException {
    Client client;
    client = new UDPClient();
    client.createConnection(ip, port);
    client.sendRequest(request);
    String response = "";
    while (true) {
      try {
        response = client.receiveReply();
      } catch (SocketTimeoutException e) {
        logger.severe("Server response timed out!!");
      }
      break;
    }
    if (response.isEmpty()) {
      return client;
    }
    logger.info("Received response from UDP Server: \"" + response + "\"");
    client.closeConnections();
    return client;
  }

  private static void handleTCPClient(Scanner sc, String ip, int port) throws IOException {
    Client client;
    client = new TCPClient();
    client.createConnection(ip, port);
    // For demo of 5 PUTs, 5 GETs and 5 DELETEs plus some additional cases that I prepared.
    performInitialOperationsOnClient(client, ip, port, TCP);
    while (true) {
      logger.info(REQUEST_FOR_INPUT);
      String operation = sc.nextLine();
      if (operation.equalsIgnoreCase(EXIT)) {
        break;
      }
      if (!isValidInput(operation)) {
        logger.info("Entered input was incorrect, please try again!");
        continue;
      }
      sendRequestsFromTCPClient(client, operation);
      if (!client.isAlive()) {
        break;
      }
    }
    if (!client.isAlive()) {
      return;
    }
    client.sendRequest(EXIT);
    client.closeConnections();
  }

  private static void sendRequestsFromTCPClient(Client client, String operation)
      throws IOException {
    client.sendRequest(operation);
    String response = "";
    while (true) {
      try {
        response = client.receiveReply();
      } catch (SocketTimeoutException e) {
        logger.severe("Server response timed out!!");
      }
      break;
    }
    if (response.isEmpty()) {
      client.closeConnections();
      return;
    }
    logger.info("Response received from server : \"" + response.trim() + "\"");
  }

  private static void performInitialOperationsOnClient(Client client, String ip, int port,
      String protocol) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
      String line;
      while ((line = reader.readLine()) != null) {
        if (protocol.equals(TCP)) {
          sendRequestsFromTCPClient(client, line);
        } else {
          performUDPClientOperation(ip, port, line);
        }
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static boolean isValidInput(String operation) {
    return operation.matches("PUT:(.*)")
        || operation.matches("GET:(.*)")
        || operation.matches("DELETE:(.*)")
        || operation.equalsIgnoreCase(EXIT)
        || operation.contains("wait");
  }

}
