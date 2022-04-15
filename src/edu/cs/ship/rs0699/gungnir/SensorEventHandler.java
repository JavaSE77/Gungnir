package edu.cs.ship.rs0699.gungnir;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class SensorEventHandler {

  public Long sensorTime = System.currentTimeMillis();
  public long lastRecord = System.currentTimeMillis();
  //this will prevent the sensors from being read out of order
  public boolean hasRecord = false;

  public SensorEventHandler() {
    
  }


  /**
   * function handles the listener for all sensors connected to the machine
   * This will check the sensors 25 (a), 24 (b), 23 (c). 
   * When all have been changed, it will write the data to the CSV file.
   * */
  public void registerListener(GpioPinDigitalInput sensor, CSVhandler CSV, boolean verbose, boolean master) {
    // create and register gpio pin listener
    sensor.addListener(new GpioPinListenerDigital() {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            
            //State is LOW (meaning beam has been broken)
            if(event.getState().toString().contains("LOW")) {
              sensorTime = System.currentTimeMillis();
            } else {
              //state is not LOW, meaning the beam has been restored
              if (verbose) System.out.println(event.getPin() + " changed " + (System.currentTimeMillis() - sensorTime) + "ms ago");
              if (master) {
                //Make sure we are not adding duplicate records, we are making sure that
                //the time sense last record is at least 100ms
                if((System.currentTimeMillis() - lastRecord) > 100) {
                  lastRecord = System.currentTimeMillis();
                  hasRecord = true;
                  //add a part in here to prevent duplicate records
                  CSV.addRecord();
                }
              }
            }
            
        }

    });
  }
  
  
  
}
