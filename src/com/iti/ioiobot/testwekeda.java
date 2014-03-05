package com.iti.ioiobot;

import java.util.Timer;
import java.util.TimerTask;

public class testwekeda {
 
 
 public testwekeda() {
	// TODO Auto-generated constructor stub
	 new Timer().schedule(new TimerTask() {
	     @Override
	     public void run() {
	    	 updatemsg();
	     }
	}, 2000, 2000);
}
 
 void updatemsg()
 {
	 DataSocket.SendMsg("Test y3ni we Keda");
 }
}
