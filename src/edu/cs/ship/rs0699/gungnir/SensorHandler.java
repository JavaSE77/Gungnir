package edu.cs.ship.rs0699.gungnir;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class SensorHandler {
  
  private GpioController gpio;
  private Pin pin;

  public SensorHandler(GpioController gpio, Pin pin) {
    this.gpio = gpio;
    this.pin = pin;
  }
  
  /**
   * sets up the pin and registers a listener for it. 
   * This does not need it's own thread. Thank goodness
   * @return 
   * */
  public GpioPinDigitalInput setupPin() {
    // provision gpio pin as an input pin with its internal pull down resistor enabled
    final GpioPinDigitalInput sensor = gpio.provisionDigitalInputPin(pin, PinPullResistance.PULL_UP);

    // set shutdown state for this input pin
    sensor.setShutdownOptions(true);


    if (Main.verbose) System.out.println(" ... complete the GPIO " + pin.getName() + " circuit and see the listener feedback here in the console.");
    return sensor;
  }
  
}
