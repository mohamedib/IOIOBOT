package com.iti.ioiobot;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;

public class DCMotor {
	public static final int MAX_SPEED = 2000;
	
	private IOIO ioio = IOIO_Looper.mIOIO;
	
	private PwmOutput PWM;
	private DigitalOutput EN;
	private DigitalOutput IN1;
	private DigitalOutput IN2;
	
	private static final int FREQ = 100;
	public DCMotor(int EN_Pin,int PWM_Pin,int Direction_Pin1,int Direction_Pin2) 
	{
		try {
			EN  = ioio.openDigitalOutput(EN_Pin, true);
			PWM  = ioio.openPwmOutput(PWM_Pin, FREQ);
			IN1 = ioio.openDigitalOutput(Direction_Pin1, false);
			IN2 = ioio.openDigitalOutput(Direction_Pin2, false);
		} catch (ConnectionLostException e) {
			e.printStackTrace();
		}
	}
	
	public void Enable(boolean En) throws ConnectionLostException
	{
		EN.write(En);
	}
	
	public void Forward()
	{
		Forward(MAX_SPEED);
	}
	
	public void Forward(float Speed)
	{
		try {
			IN1.write(true);
			IN2.write(false);
			//PWM.setPulseWidth(Speed);
			PWM.setDutyCycle(Speed);
		} catch (ConnectionLostException e) {
			e.printStackTrace();
		}
		
	}
	
	public void Backward()
	{
		Backward(MAX_SPEED);
	}
	
	public void Backward(float Speed)
	{
		try {
			IN1.write(false);
			IN2.write(true);
			//PWM.setPulseWidth(Speed);
			PWM.setDutyCycle(Speed);
		} catch (ConnectionLostException e) {
			e.printStackTrace();
		}
		
	}
	
	public void Stop()
	{
		try {
			PWM.setPulseWidth(0);
			IN1.write(false);
			IN2.write(false);	
		} catch (ConnectionLostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
