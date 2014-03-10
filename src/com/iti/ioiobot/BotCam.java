package com.iti.ioiobot;

import java.io.BufferedOutputStream;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BotCam {
	Camera camera;
    SurfaceView preview;
    SurfaceHolder previewHolder;
    void startCamera(){
		/*try {
			remoteConnectionServer = new ServerSocket(CSERVERPORT);
			remoteConnection = remoteConnectionServer.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		if(camera != null){
			camera.release();
			camera = null;
		}

		try {
			HandlerThread imageThread = new HandlerThread("imageSender");
			imageThread.setDaemon(true);
			imageThread.start();
		

			final Handler imageSender = new Handler(imageThread.getLooper());
//			final BufferedOutputStream output = new BufferedOutputStream(remoteConnection.getOutputStream());
			final int w = 352; final int h = 288;
			if (camera == null) {
				camera=Camera.open();
				camera.setDisplayOrientation(90);
				preview=MainActivity.sv;
				previewHolder = preview.getHolder();
				camera.setPreviewDisplay(previewHolder);
				previewHolder.addCallback(surfaceCallback);
				previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
				Parameters params = camera.getParameters(); 
				params.setPreviewFormat(ImageFormat.NV21);
				params.setPreviewSize(w,h);
				params.setJpegQuality(20);
				camera.setParameters(params);
			}
			camera.setPreviewCallback(new PreviewCallback() {
				long timeout = SystemClock.uptimeMillis(); 
				//a variable that carry the system lifetime and it may leads to an error due to overflow 
				final long delay = 50; //the dealy between each image and the other in ms

				public void onPreviewFrame(final byte[] data, Camera arg1) {

					if (SystemClock.uptimeMillis() > timeout  && ServerThread.remoteConnection.isConnected()){
						//check if the delay is passed
						imageSender.post(new Runnable(){ 
							//the thread which takes images from camera and send them to client
							public void run(){
								try {
									final BufferedOutputStream output = new BufferedOutputStream(ServerThread.remoteConnection.getOutputStream());

									YuvImage frame = new YuvImage(data, ImageFormat.NV21, w, h, null); //get the image from the camera with an android image format nv21																		
									frame.compressToJpeg(new Rect(0, 0, w, h), 80,output); //convert the image to jpeg format with 80% quality and save them in the buffer
									output.flush(); //to ensure all pending data is written out to the target stream
								} catch (Exception e) {
					//				console(e);
								}

							}
						});

						timeout = SystemClock.uptimeMillis() + delay;  //add the delay so that it can be compared in the next time
					}
				}

			});

			camera.startPreview();
		//	streaming.pause();
		//	console("Camera started");
		} catch (Exception e) {
		//	console(e);
		}
	}

	SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera.setPreviewDisplay(previewHolder);
			} catch (Exception e) {
				//console(e);
			}
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
			camera.startPreview();
		}

		public void surfaceDestroyed(SurfaceHolder holder) {		}
	};


}
