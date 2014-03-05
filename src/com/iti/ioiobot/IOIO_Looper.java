package com.iti.ioiobot;

import android.os.Handler;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;

public class IOIO_Looper extends BaseIOIOLooper {
	static IOIO mIOIO; 
	Handler h = MainHandler.getMainHandler();
	
	
	@Override
	protected void setup() throws ConnectionLostException {
		mIOIO = ioio_;
		h.obtainMessage(0x11).sendToTarget();
	}

	@Override
	public void loop() throws ConnectionLostException {
		//we must override this function
	
	}
	
	@Override
	public void disconnected() {
		super.disconnected();
		h.obtainMessage(0x10).sendToTarget();
	}
	
}

