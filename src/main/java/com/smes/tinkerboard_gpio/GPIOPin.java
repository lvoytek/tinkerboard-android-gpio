package com.smes.tinkerboard_gpio;


public class GPIOPin extends ExecuteAsRootBase
{
	private int pinNumber;
	private boolean isOutput;
	private boolean available;

	public GPIOPin(int pinNumber)
	{
		this.pinNumber = pinNumber;
		this.isOutput = false;

		this.available = canRunRootCommands();

		if(this.available)
			this.execute("echo " + this.pinNumber + " > /sys/class/gpio/export");
	}

	public void setDirection(boolean output)
	{
		this.isOutput = output;
		this.execute("echo \"" + ((output) ? "out" : "in") + "\" > /sys/class/gpio/gpio"
				+ this.pinNumber + "/direction");
	}

	public void setOutput(boolean high)
	{
		this.execute("echo " + ((high) ? "1" : "0") + " > /sys/class/gpio/gpio"
				+ this.pinNumber + "/value");
	}
}
