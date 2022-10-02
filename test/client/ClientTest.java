package client;

import java.io.IOException;
import org.junit.Test;

public class ClientTest {

  @Test
  public void testServerTimeout() throws IOException {
    ClientApp.main(new String[]{"localhost","6000","udp"});
  }
}
