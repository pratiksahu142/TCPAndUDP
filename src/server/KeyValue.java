package server;

import java.util.HashMap;

public class KeyValue {

  private final HashMap<String, String> keyValueStore;

  public KeyValue() {
    keyValueStore = new HashMap<>();
  }

  public String putKeyValue(String key, String value) {
    return keyValueStore.put(key, value);
  }

  public String getValue(String key) {
    return keyValueStore.get(key);
  }

  public String deleteValue(String key) {
    return keyValueStore.remove(key);
  }
}
