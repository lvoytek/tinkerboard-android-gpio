package com.smes.tinkerboard_gpio.sensors;

import com.smes.tinkerboard_gpio.GPIOPin;

public class PressureSensor extends Sensor
{
	GPIOPin sensorPin;
	public PressureSensor(String name, int pin)
	{
		super(name);
		this.sensorPin = new GPIOPin(pin);
		this.sensorPin.setDirection(false);
	}

	@Override
	public int getInput()
	{
		return this.sensorPin.getInput();
	}

	@Override
	public void setOutput(int output)
	{

	}

	@Override
	public String getSensorType()
	{
		return "Pressure Sensor";
	}
}
