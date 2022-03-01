package edu.cs.ship.rs0699.engr499;

public class UserSettings {

  private double angle = 0;
  private double weight = 0;
  private static UserSettings singleton = null;
  
  //this is a singleton
  public UserSettings() {
    //singleton
  }
  
  
  /**
   * Get instance
   * @return the instance of the userSettings class
   * */
  public static UserSettings getInstance() {
    if (singleton == null)
      singleton = new UserSettings();

    return singleton;
  }
  
  /**
   * Set the angle of the machine
   * @param double angle. Only accepts angles < 180. 
   * */
  public void setAngle(double angle) { 
    this.angle = angle % 180;
  }
  
  /**
   * Set the weight of the slider
   * @param double weight
   * */
  public void setWeight(double weight) { 
    this.weight = weight;
  }
  
  /**
   * Get the weight of the machine
   * @return the weight of the machine
   * */
  public double getWeight() { 
    return this.weight;
  }
  
  /**
   * Get the angle of the machine
   * @return the angle of the machine
   * */
  public double getAngle() { 
    return this.angle;
  }
  
  
}
