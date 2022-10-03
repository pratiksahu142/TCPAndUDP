package server;

/**
 * Represents utility class to prepare results to be sent to the client.
 */
public class Response {

  public static final String INSERTED = "Successful new insertion!";
  public static final String OVERRIDEN = "Successfully overriden value for existing key-value!";
  public static final String KEY_NOT_PRESENT = "Could not find value for the key";
  public static final String DELETED = "Successful deletion!";
  public static final String NO_KEY_TO_DELETE = "Could not find key to delete!";

  public static String getValueSuccessful(String key, String value) {
    return "Fetched the value \"" + value + "\" for the key \"" + key + "\"";
  }
}
