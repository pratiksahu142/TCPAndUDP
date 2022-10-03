package server;

import java.util.logging.Logger;

/**
 * Abstraction class for {@link Server} interface containing common implementation methods for both
 * TCP and UDP server implementations.
 */
public abstract class AbstractHandler implements Server {

  private static final String PUT = "PUT";
  private static final String GET = "GET";
  private static final String DELETE = "DELETE";
  private static final String DELIMITER_COLON = ":";
  private static final Logger logger = Logger.getLogger(AbstractHandler.class.getName());
  private static final String MALFORMED = "malformed";
  private KeyValue keyValue;

  protected void setKeyValue(KeyValue keyValue) {
    this.keyValue = keyValue;
  }

  @Override
  public String performOperation(String request) {
    String[] reqArr = request.split(DELIMITER_COLON);
    if (reqArr[0].equalsIgnoreCase(PUT) && reqArr.length == 3) {
      String key = reqArr[1];
      String value = reqArr[2];
      String status = keyValue.putKeyValue(key, value);
      if (status == null) {
        logger.info("Value :\"" + value + "\" inserted for key :\"" + key + "\".");
        return Response.INSERTED;
      } else {
        logger.info("Value :\"" + value + "\" overriden for key :\"" + key + "\".");
        return Response.OVERRIDEN;
      }
    } else if (reqArr[0].equalsIgnoreCase(GET) && reqArr.length == 2) {
      String key = reqArr[1];
      String value = keyValue.getValue(key);
      if (value == null) {
        logger.warning("Could not find key :\"" + key + "\".");
        return Response.KEY_NOT_PRESENT;
      }
      return Response.getValueSuccessful(key, value);
    } else if (reqArr[0].equalsIgnoreCase(DELETE) && reqArr.length == 2) {
      String key = reqArr[1];
      String status = keyValue.deleteValue(key);
      if (status != null) {
        logger.info("Entry with key :\"" + key + "\" deleted.");
        return Response.DELETED;
      } else {
        logger.warning("Could not find key :\"" + key + "\" to delete.");
        return Response.NO_KEY_TO_DELETE;
      }
    }
    return MALFORMED;
  }
}
