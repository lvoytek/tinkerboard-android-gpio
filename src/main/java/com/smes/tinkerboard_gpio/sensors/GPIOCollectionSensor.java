package com.smes.tinkerboard_gpio.sensors;

import com.smes.tinkerboard_gpio.GPIOCollection;
import com.smes.tinkerboard_gpio.GPIOPin;

public abstract class GPIOCollectionSensor
{
	GPIOPin [] gpioPinCollection;
	GPIOCollection[] collections;

	public GPIOCollectionSensor(int numPins)
	{
		this.gpioPinCollection = new GPIOPin[numPins];
		this.collections = new GPIOCollection[1];
		this.collections[0] = new GPIOCollection(gpioPinCollection);
	}

	public GPIOCollectionSensor(int numPins, int numCollections)
	{
		this.gpioPinCollection = new GPIOPin[numPins];
		this.collections = new GPIOCollection[numCollections];
	}

	public void setAsOutput()
	{
		for(GPIOCollection pinCollection : this.collections)
		{
			pinCollection.setDirectionAll(true);
		}
	}

	public void setAsInput()
	{
		for(GPIOCollection pinCollection : this.collections)
		{
			pinCollection.setDirectionAll(false);
		}
	}

	public abstract int getInput();
	public abstract void setOutput(int output);
}
