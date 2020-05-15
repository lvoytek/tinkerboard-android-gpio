package com.smes.tinkerboard_gpio.sensors;

public abstract class Sensor
{
	protected String name;

	public Sensor(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	public abstract int getInput();
	public abstract void setOutput(int output);
	public abstract String getSensorType();
}
