package com.iti.ioiobot; 

import java.net.Socket;

import android.app.Application;
import android.content.Context;


public class App extends Application {

	//This Class Should be Initialized Only from MainActivity.java
	//This Class shouldn't be updated from any other class
	public static Context ApplicationContext; //Set by MainActivity.java
	
	public static Bot Robot; //Set by MainActivity.java
	
	public static Socket DataSocket;   //Set by DataSocket.java
	public static Socket VideoSocket;  //Set by VideoSocket.java
	
	public static void setRobotInstance(Bot b)
	{
		Robot = b;
	}
	
	public static void setDataSocketInstance(Socket s)
	{
		DataSocket = s;
	}
	
	public static void setVideoSocketInstance(Socket s)
	{
		VideoSocket = s;
	}
}
