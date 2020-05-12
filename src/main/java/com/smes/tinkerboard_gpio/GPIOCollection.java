package com.smes.tinkerboard_gpio;

import java.util.ArrayList;

public class GPIOCollection extends ExecuteAsRootBase
{
	int[] pins;
	public GPIOCollection(int[] pins)
	{
		this.pins = pins;
	}

	public GPIOCollection(GPIOPin[] pins)
	{
		this.pins = new int[pins.length];

		for(int i = 0; i < this.pins.length; i++)
		{
			this.pins[i] = pins[i].getPinNumber();
		}
	}

	public void setDirectionAll(boolean output)
	{
		ArrayList<String> setDirCommands = new ArrayList<String>();

		for(int pin : pins)
		{
			setDirCommands.add("echo \"" + ((output) ? "out" : "in") + "\" > /sys/class/gpio/gpio"
					+ pin + "/direction");
		}

		this.execute(setDirCommands);
	}

	public void setOutputAll(boolean high)
	{
		ArrayList<String> setOutCommands = new ArrayList<String>();

		for(int pin : pins)
		{
			setOutCommands.add("echo " + ((high) ? "1" : "0") + " > /sys/class/gpio/gpio"
					+ pin + "/value");
		}

		this.execute(setOutCommands);
	}
}
