package com.iti.ioiobot;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class ServerThread implements Runnable {

   Handler h = MainHandler.getMainHandler(); 
    // DESIGNATE A PORT
    public static final int SERVERPORT = 1234;
    private ServerSocket serverSocket;
    Socket client = new Socket();
    BotCam cam=new BotCam();
    //camera socket
    public static final int CSERVERPORT = 1235;
	private ServerSocket remoteConnectionServer;
	public static Socket remoteConnection ;
    
    
    @Override
	public void run() {
    	
    	 try {
			serverSocket = new ServerSocket(SERVERPORT);
			remoteConnectionServer = new ServerSocket(CSERVERPORT); //open camera socket
			Log.v("Server","Server Started");
			while (true) {
	             // LISTEN FOR INCOMING CLIENTS
				
				if (!client.isConnected()){
					Log.v("ServerThread", "Wait for Client...");
					client = serverSocket.accept();
					remoteConnection = remoteConnectionServer.accept();//camera client
					Log.v("Server", "Connected");
					h.obtainMessage(0xA1).sendToTarget();
					cam.startCamera();
					new Thread(new DataSocket(client)).start();
				}
				
				
				
		//		else{
			//		Log.v("ServerThread",Boolean.toString(serverSocket.isClosed()));
				//Log.v("ServerThread",Boolean.toString(client.isClosed()));
				
				//}
	    	 }	
		} catch (IOException e) {
			Log.v("Server","Server Disconnected");
			
		}
    	 
	}
    
    
}



