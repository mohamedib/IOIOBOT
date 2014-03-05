package com.iti.ioiobot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.Handler;
import android.util.Log;

public class DataSocket implements Runnable {
	private Socket _client;
	String line = null;
	BufferedReader in;
	Handler mHandler;

	static PrintWriter out;

	public DataSocket(Socket client) {
		_client = client;
		mHandler = MainHandler.getMainHandler();
		App.setDataSocketInstance(_client);
	}

	@Override
	public void run() {

		Log.v("DataAnalyst", "DataAnalyst Started");
		try {

			if (_client.isConnected()) {
				in = new BufferedReader(new InputStreamReader(
						_client.getInputStream()));
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(_client.getOutputStream())),
						true);

				// gpsinitread g = new gpsinitread();
				// g.GpsInit(App.LOCATION_SERVICE);
				// g.start();

			}

			while ((line = in.readLine()) != null) {

				Log.v("ServerActivity", Character.toString(line.charAt(0)));
				Log.v("ServerActivity", line.toString());

				switch (line.charAt(0)) {
				// Forward, Backward
				case 'D': // #LAST UPDATE: REVERSED
					mHandler.obtainMessage(BOT_CMMANDS.FORWARD).sendToTarget();
					break;

				case 'U':
					mHandler.obtainMessage(BOT_CMMANDS.BACKWARD).sendToTarget();
					break;

				// Left, Right
				case 'L':
					mHandler.obtainMessage(BOT_CMMANDS.CIRCULAR_LEFT)
							.sendToTarget();
					break;

				case 'R':
					mHandler.obtainMessage(BOT_CMMANDS.CIRCULAR_RIGHT)
							.sendToTarget();
					break;

				case 'S':
					mHandler.obtainMessage(BOT_CMMANDS.STOP).sendToTarget();
					break;
				}

			}

		} catch (IOException e) {
			Log.v("Data Analyst", "Exception");
		}

	}

	public static void SendMsg(String s) {
		out.println(s);
	}

}
