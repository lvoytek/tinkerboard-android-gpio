package com.smes.tinkerboard_gpio.sensors;

import com.smes.tinkerboard_gpio.GPIO;
import com.smes.tinkerboard_gpio.GPIOPin;

public class I2CSensor extends Sensor
{
	protected byte address;
	protected GPIOPin scl;
	protected GPIOPin sda;

	public I2CSensor(String name, byte address, int scl, int sda)
	{
		super(name);
		this.address = address;
		this.scl = new GPIOPin(scl);
		this.sda = new GPIOPin(sda);
	}

	@Override
	public int getInput()
	{
		return 0;
	}

	@Override
	public void setOutput(int output)
	{

	}

	protected void writeByte(){}

	@Override
	public String getSensorType()
	{
		return "Generic I2C Sensor";
	}
}
