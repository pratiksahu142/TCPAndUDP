package server;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Represents the driver application for server implementation that handles working of server with
 * TCP and UDP both the protocols.
 */
public class ServerApp {

  public static final Logger logger = Logger.getLogger(ServerApp.class.getName());
  private static final String EXIT = "exit";
  private static final String TCP = "tcp";
  private static final String UDP = "udp";
  private static final String MALFORMED = "malformed";

  public static void main(String[] args) throws IOException, IllegalArgumentException {
    logger.info("ServerApp started ...");
    logger.info("Arguments received :");
    for (String arg : args) {
      logger.info(arg + "\n");
    }
    int port = Integer.parseInt(args[0]);
    String protocol = args[1];
    //Below code is commented out to help close server for scenarios where client is unable to connect and server keeps on waiting indefinitely
//    Scanner sc = new Scanner(System.in);
//    String ask = sc.nextLine().trim();
//    if(ask.equals(EXIT))
//      return;
    if (protocol.equalsIgnoreCase(TCP)) {
      handleTCPServerConnection(port);
    } else if (protocol.equalsIgnoreCase(UDP)) {
      handleUDPServer(port);
    } else {
      throw new IllegalArgumentException("No valid protocol (tcp/udp).");
    }
    logger.info("Server App exiting...");
  }

  private static void handleUDPServer(int port) throws IOException {
    Server server = new UDPHandler();
    logger.info("Server initialized with " + UDP + " protocol.");
    server.createServer(port);
    while (true) {
      String request = server.receiveRequest().trim();
      if (request.contains("wait")) {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      logger.info("Request received from UDP client: \"" + request + "\"");
      String response = server.performOperation(request);
      if (validateMalformedRequest(server, response)) {
        continue;
      }
      server.sendReply(response);
    }
  }

  private static boolean validateMalformedRequest(Server server, String request)
      throws IOException {
    if (request.equals(MALFORMED)) {
      logger.warning(
          "Invalid request sent by client at address \"" + server.getClientAddress() + ":"
              + server.getClientPort() + "\"");
      server.sendReply("Invalid Request!");
      return true;
    }
    return false;
  }

  public static void handleTCPServerConnection(int port) throws IOException {
    Server server = new TCPHandler();
    logger.info("Server initialized with " + TCP + " protocol.");
    server.createServer(port);
    //server.sendReply("Unsolicited packet");
    while (true) {
      String request = "";
      while (true) {
        request = server.receiveRequest();
        if (request != null) {
          logger.info("Received request : \"" + request + "\"");
          break;
        }
      }
      if (request.equalsIgnoreCase(EXIT)) {
        server.closeConnections();
        break;
      }
      if (request.contains("wait")) {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      String reply = server.performOperation(request);
      if (validateMalformedRequest(server, reply)) {
        continue;
      }

      logger.info("Operation performed : \"" + reply + "\"");
      server.sendReply(reply);
    }
  }
}
