package com.smes.tinkerboard_gpio.sensors;

import com.smes.tinkerboard_gpio.GPIOCollection;
import com.smes.tinkerboard_gpio.GPIOPin;

//Works for 17KP1604 alphanumeric keypad
//Form Factor:
//	1	2	3	A
//	4	5	6	B
//	7	8	9	C
//	0	F	E	D
public class KeyPad extends GPIOCollectionSensor
{
	public KeyPad(int R1, int R2, int R3, int R4, int C1, int C2, int C3, int C4)
	{
		super(8, 2);
		this.gpioPinCollection[0] = new GPIOPin(R1);
		this.gpioPinCollection[1] = new GPIOPin(R2);
		this.gpioPinCollection[2] = new GPIOPin(R3);
		this.gpioPinCollection[3] = new GPIOPin(R4);
		this.gpioPinCollection[4] = new GPIOPin(C1);
		this.gpioPinCollection[5] = new GPIOPin(C2);
		this.gpioPinCollection[6] = new GPIOPin(C3);
		this.gpioPinCollection[7] = new GPIOPin(C4);

		GPIOPin[] rowPins = new GPIOPin[4];
		GPIOPin[] colPins = new GPIOPin[4];

		for(int i = 0; i < 4; i++)
			rowPins[i] = this.gpioPinCollection[i];

		for(int i = 0; i < 4; i++)
			colPins[i] = this.gpioPinCollection[i + 4];

		this.collections[0] = new GPIOCollection(rowPins);
		this.collections[1] = new GPIOCollection(colPins);
	}

	private void neutralPosition(boolean row)
	{
		this.setAsOutput();
		this.collections[0].setOutputAll(!row);
		this.collections[1].setOutputAll(row);
	}

	private int testRow()
	{
		this.neutralPosition(false);

		for(int i = 0; i < 4; i++)
		{
			this.gpioPinCollection[i].setDirection(false);
			if(this.gpioPinCollection[i].getInput() == 0)
			{
				this.gpioPinCollection[i].setDirection(true);
				return i+1;
			}
		}
		return 0;
	}

	private int testColumn()
	{
		this.neutralPosition(true);
		for(int i = 4; i < 8; i++)
		{
			this.gpioPinCollection[i].setDirection(false);
			if(this.gpioPinCollection[i].getInput() == 0)
			{
				this.gpioPinCollection[i].setDirection(true);
				return i-3;
			}
		}
		return 0;
	}


	@Override
	public int getInput()
	{
		return testRow()*10 + testColumn();
	}

	@Override
	public void setOutput(int output)
	{

	}
}
