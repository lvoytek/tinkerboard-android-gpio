package com.smes.tinkerboard_gpio.sensors;

public class PulseOximeter extends I2CSensor
{
	public PulseOximeter(String name, byte address, int scl, int sda)
	{
		super(name, (byte)0xAE, scl, sda);
	}
}
