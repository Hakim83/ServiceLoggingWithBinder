package course.examples.services.loggingservicewithbinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LoggingService extends Service {
    // Binder given to clients
    private final IBinder mBinder = new LoggingBinder();

    private static final String TAG = "LoggingService";

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void logMessage(String message){
        Log.i(TAG, message);
    }

    public class LoggingBinder extends Binder {
        LoggingService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LoggingService.this;
        }
    }
}
