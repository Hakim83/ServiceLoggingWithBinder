package course.examples.services.loggingservicewithbinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoggingServiceClient extends Activity {

	private final static String TAG = "LoggingServiceClient";
    LoggingService mService;
	private boolean mIsBound;

	// Object implementing Service Connection callbacks
	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LoggingService, cast the IBinder and get LoggingService instance
            LoggingService.LoggingBinder binder = (LoggingService.LoggingBinder) service;
            mService = binder.getService();
			mIsBound = true;
		}

		public void onServiceDisconnected(ComponentName className) {
            mIsBound = false;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		final Button buttonStart = (Button) findViewById(R.id.buttonStart);
		buttonStart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mIsBound) {
                    mService.logMessage("this is message");
				}
			}
		});
	}

	// Bind to LoggingService
	@Override
	protected void onResume() {
		super.onResume();

        Intent serviceIntent = new Intent(this, LoggingService.class);
		bindService(serviceIntent, mConnection,	Context.BIND_AUTO_CREATE);
	}

	// Unbind from the LoggingService
	@Override
	protected void onPause() {

		if (mIsBound)
			unbindService(mConnection);

		super.onPause();
	}
}