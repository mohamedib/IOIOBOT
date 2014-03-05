package com.iti.ioiobot;

public class Bot {
	
	
	MotorDriver mDriver = new MotorDriver();
	DCMotor Motor_1 = new DCMotor(0,  3,  4,  5);
	DCMotor Motor_2 = new DCMotor(1, 10, 11, 12);
	
	
	public Bot()
	{
		mDriver.AttachMotor(Motor_1);
		mDriver.AttachMotor(Motor_2);
		App.setRobotInstance(this);
	}
	
	public void Stop()
	{
		mDriver.Motors.get(0).Stop();
		mDriver.Motors.get(1).Stop();
	}
	public void MoveForward(float speed)
	{
		//mDriver.Motors.get(0).Forward();
		//mDriver.Motors.get(1).Backward();
		
		mDriver.Motors.get(0).Forward(speed);
		mDriver.Motors.get(1).Backward(speed);
	}
	
	public void MoveBackward(float speed)
	{
		//mDriver.Motors.get(0).Backward();
		//mDriver.Motors.get(1).Forward();
		mDriver.Motors.get(0).Backward(speed);
		mDriver.Motors.get(1).Forward(speed);
	}
	
	
	public void CircularLeft(float speed)
	{
		//mDriver.Motors.get(0).Forward();
		//mDriver.Motors.get(1).Forward();
		
		mDriver.Motors.get(0).Forward(speed);
		mDriver.Motors.get(1).Forward(speed);
	}
	
	public void CircularRight(float speed)
	{
		//mDriver.Motors.get(0).Backward();
		//mDriver.Motors.get(1).Backward();
		
		mDriver.Motors.get(0).Backward(speed);
		mDriver.Motors.get(1).Backward(speed);
	}
}
