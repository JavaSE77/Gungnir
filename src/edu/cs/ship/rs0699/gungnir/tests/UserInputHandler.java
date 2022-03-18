package edu.cs.ship.rs0699.gungnir.tests;

import edu.cs.ship.rs0699.gungnir.InputHandler;
import edu.cs.ship.rs0699.gungnir.UserSettings;
import junit.framework.TestCase;

public class UserInputHandler extends TestCase {

  public void testHandler() {
    InputHandler handler = new InputHandler();
    assertNotNull(handler);
    int response = handler.handleInput("invalid uri");
    assertEquals(response, 400);
  }
  
  public void testResponseCode200() {
    InputHandler handler = new InputHandler();
    String uri = "/input/?angle=45&weight=5&name=Anonymous";
    int response = handler.handleInput(uri);
    assertEquals(response, 200);

  }
  
  public void testResponseCode400() {
    InputHandler handler = new InputHandler();
    String uri = "/input/?angle=45*weight=5&name=Anonymous";
    int response = handler.handleInput(uri);
    assertEquals(response, 400);
  }
  
  public void testResponseCodeBigValue() {
    InputHandler handler = new InputHandler();
    String uri = "/input/?angle=450000&weight=5&name=Anonymous";
    int response = handler.handleInput(uri);
    assertEquals(response, 200);
  }
  
  public void testResponseCodeGarbageAngle() {
    InputHandler handler = new InputHandler();
    String uri = "/input/?angle=garbage&weight=50&name=Anonymous";
    int response = handler.handleInput(uri);
    assertEquals(response, 400);
  }
  
  public void testResponseCodeGarbageWeight() {
    InputHandler handler = new InputHandler();
    String uri = "/input/?angle=45&weight=garbage&name=Anonymous";
    int response = handler.handleInput(uri);
    assertEquals(response, 400);
  }
  
  public void testResponseCodeTestNull() {
    InputHandler handler = new InputHandler();
    String uri = "/input/?angle=&weight=&name=Anonymous";
    int response = handler.handleInput(uri);
    assertEquals(response, 400);
  }
  
  
  public void testAngleSetViaURI() {

    UserSettings input = UserSettings.getInstance();
    input.setAngle(0);
    assertEquals(input.getAngle(), 0.0);
    InputHandler handler = new InputHandler();
    String uri = "/input/?angle=45&weight=5&name=Anonymous";
    int response = handler.handleInput(uri);
    assertEquals(response, 200);
    assertEquals(input.getAngle(), 45.0);
  }
  
  public void testWeightSetViaURI() {

    UserSettings input = UserSettings.getInstance();
    input.setWeight(0);
    assertEquals(input.getWeight(), 0.0);
    InputHandler handler = new InputHandler();
    String uri = "/input/?angle=45&weight=5&name=Anonymous";
    int response = handler.handleInput(uri);
    assertEquals(response, 200);
    assertEquals(input.getWeight(), 5.0);
  }  
  
  public void testUserSetViaURI() {

    UserSettings input = UserSettings.getInstance();
    input.setUser("default");;
    assertEquals(input.getUser(), "default");
    InputHandler handler = new InputHandler();
    String uri = "/input/?angle=45&weight=5&name=Anonymous";
    int response = handler.handleInput(uri);
    assertEquals(response, 200);
    assertEquals(input.getUser(), "Anonymous");
  }
}
