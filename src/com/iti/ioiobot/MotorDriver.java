package com.iti.ioiobot;

import java.util.ArrayList;

public class MotorDriver {

	public ArrayList<DCMotor> Motors = new ArrayList<DCMotor>();
	
	public void AttachMotor(int ID, int EN_Pin,int PWM_Pin,int Direction_Pin1,int Direction_Pin2)
	{
		DCMotor m = new DCMotor(EN_Pin, PWM_Pin,Direction_Pin1, Direction_Pin2);
		Motors.add(ID, m);
		
	}
	
	public int AttachMotor(DCMotor motor)
	{
		Motors.add(motor);
		return Motors.indexOf(motor);
	}
	
	public void AllON()
	{
		for (DCMotor d : Motors){
			d.Forward(DCMotor.MAX_SPEED);
		}
	}
	
}
