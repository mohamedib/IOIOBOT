package com.iti.ioiobot;

import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.extremegtx.ioiobot.R;

public class MainActivity extends IOIOActivity implements Callback {

	public static Bot Robot;
	public static boolean destroy = false;
	public static SurfaceView sv;
	private TextView TextV1;
	private TextView TextV2;
	public static TextView ip;
	private SeekBar sb;
	public static String SERVERIP = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		TextV1 = (TextView) findViewById(R.id.textView1);
		TextV2 = (TextView) findViewById(R.id.textView2);
		ip = (TextView) findViewById(R.id.ip);
		sv = (SurfaceView) findViewById(R.id.preview);
		sb = (SeekBar) findViewById(R.id.seekBar1);

		new MainHandler(new Handler(getMainLooper(), this));
		App.ApplicationContext = getApplicationContext();
		SERVERIP = getLocalIpAddress();
		ip.setText("ip:" + SERVERIP);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// destroy=true;
		try {
			ServerThread.serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ServerThread.remoteConnectionServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected IOIOLooper createIOIOLooper() {
		return new IOIO_Looper();
	}

	public static void init() {
		Log.v("MyApp", "Init...");
		Robot = new Bot();
		new Thread(new ServerThread()).start();

	}

	@Override
	public boolean handleMessage(Message msg) {

		Log.v("MainHandler", Integer.toString(msg.what));

		switch (msg.what) {
		case BOT_CMMANDS.FORWARD:
			Robot.MoveForward((sb.getProgress() / 10.0f));
			Log.v("MyApp", "Forward");
			break;
		case BOT_CMMANDS.BACKWARD:
			Robot.MoveBackward((sb.getProgress() / 10.0f));

			break;
		case BOT_CMMANDS.CIRCULAR_LEFT:
			Robot.CircularLeft((sb.getProgress() / 10.0f));
			break;
		case BOT_CMMANDS.CIRCULAR_RIGHT:
			Robot.CircularRight((sb.getProgress() / 10.0f));
			break;
		case BOT_CMMANDS.STOP:
			Robot.Stop();

		case 0x10:
			TextV1.setText("IOIO: Not Connected ");
			break;

		case 0x11:
			TextV1.setText("IOIO: I'm Up and Running :D");
			init();
			break;

		case 0xA1:
			TextV2.setText("Server: New Client Connected");
			break;

		}
		return false;
	}

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& validIP(inetAddress.getHostAddress())) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (Exception e) {
			// Log.e(TAG, e.toString());
		}
		return null;

	}

	/** check if string is valid ipv4 */
	public static boolean validIP(String ip) {
		if (ip == null || ip.isEmpty())
			return false;
		ip = ip.trim();
		if ((ip.length() < 6) & (ip.length() > 15))
			return false;

		try {
			Pattern pattern = Pattern
					.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
			Matcher matcher = pattern.matcher(ip);
			return matcher.matches();
		} catch (Exception e) {
			return false;
		}
	}
}
