package com.iti.ioiobot;



import com.extremegtx.ioiobot.R;

import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends IOIOActivity implements Callback {

	public static Bot Robot;
	public static SurfaceView sv;
	private TextView TextV1;
	private TextView TextV2;
	private SeekBar sb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		TextV1 = (TextView) findViewById(R.id.textView1);
		TextV2 = (TextView) findViewById(R.id.textView2);
		sv = (SurfaceView) findViewById(R.id.preview);
		sb = (SeekBar) findViewById(R.id.seekBar1);

		new MainHandler(new Handler(getMainLooper(), this));

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

}
