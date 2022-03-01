package edu.cs.ship.rs0699.gungnir;

public class SensorInput {

  /*This is a heavily abstracted "idea" of what sensor input is. 
  * this is going to provide some "junk" data with runs.
  */
  
  long sensor1 = 0;
  long sensor2 = 0;
  long sensor3 = 0;
  long sensor4 = 0;

  private static SensorInput singleton = null;
  //this needs to be a singleton
  private SensorInput() {
    sensor1 = System.nanoTime();
    sensor2 = sensor1 + 150;
    sensor3 = sensor2 + 150;
    sensor4 = sensor3 + 150;
  }
  
  // Static method
  // Static method to create instance of Singleton class
  public static SensorInput getInstance()
  {
      if (singleton == null)
        singleton = new SensorInput();

      return singleton;
  }
  
  
  /** 
   * long[] get Sensor Data
   * returns an array of longs containing the time in
   * nanoseconds the sensors were broken at.
   * */
  public long[] getSensorData() {
    long[] sensorData = new long[4];
    sensorData[0] = sensor1;
    sensorData[1] = sensor2;
    sensorData[2] = sensor3;
    sensorData[3] = sensor4;
    return sensorData;
  }
  
}
